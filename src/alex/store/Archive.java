package alex.store;

import java.io.IOException;
import java.util.Arrays;

import alex.io.InputStream;
import alex.io.OutputStream;
import alex.util.bzip2.BZip2Compressor;
import alex.util.bzip2.BZip2Decompressor;
import alex.util.crc32.CRC32HGenerator;
import alex.util.gzip.GZipCompressor;
import alex.util.gzip.GZipDecompressor;
import alex.util.lzma.LZDecompressor;
import alex.util.lzma.RsBuffer;
import alex.util.whirlpool.Whirlpool;
import alex.utils.Constants;

public class Archive {

	private int id;
	private int revision;
	private int compression;
	private byte[] data;
	private int[] keys;

	protected Archive(int id, byte[] archive, int[] keys) {
		this.id = id;
		this.keys = keys;
		decompress(archive);

	}

	public Archive(int id, int compression, int revision, byte[] data) {
		this.id = id;
		this.compression = compression;
		this.revision = revision;
		this.data = data;
	}

	public byte[] compress() {
		OutputStream stream = new OutputStream();
		stream.writeByte(compression);
		byte[] compressedData;
		switch (compression) {
		case Constants.NO_COMPRESSION: // no compression
			compressedData = data;
			stream.writeInt(data.length);
			break;
		case Constants.BZIP2_COMPRESSION:
			compressedData = BZip2Compressor.compress(data);
			stream.writeInt(compressedData.length);
			stream.writeInt(data.length);
			break;
		case 3:
			throw new UnsupportedOperationException("LZMA IS NOT SUPPORTED YOU DUMB FREAK BASTARD");
		default: // gzip
			compressedData = GZipCompressor.compress(data);
			stream.writeInt(compressedData.length);
			stream.writeInt(data.length);
			break;
		}
		stream.writeBytes(compressedData);
		if (keys != null && keys.length == 4) {
			stream.encodeXTEA(keys, 5, stream.getOffset());
		}
		if (revision != -1) {
			stream.writeShort(revision);
		}
		byte[] compressed = new byte[stream.getOffset()];
		stream.setOffset(0);
		stream.getBytes(compressed, 0, compressed.length);
		return compressed;
	}

	private void decompress(byte[] archive) {
		// try {
			InputStream stream = new InputStream(archive);
			if (keys != null && keys.length == 4) {
				stream.decodeXTEA(keys);
			}
			compression = stream.readUnsignedByte();
			int compressedLength = stream.readInt();
			if (compressedLength < 0 || compressedLength > Constants.MAX_VALID_ARCHIVE_LENGTH) {
				throw new RuntimeException("INVALID ARCHIVE HEADER");
			}
			switch (compression) {
			case Constants.NO_COMPRESSION: // no compression
				data = new byte[compressedLength];
				checkRevision(compressedLength, archive, stream.getOffset());
				stream.readBytes(data, 0, compressedLength);
				break;
			case Constants.BZIP2_COMPRESSION: // bzip2
				int length = stream.readInt();
				if (length <= 0) {
					data = null;
					break;
				}
				data = new byte[length];
				checkRevision(compressedLength, archive, stream.getOffset());
				BZip2Decompressor.decompress(data, length, archive, compressedLength, 9);
				break;
			case 3:
				length = stream.readInt();
				if (length <= 0) {
					data = null;
					break;
				}
				try {
					data = LZDecompressor.method5579(new RsBuffer(Arrays.copyOfRange(archive, 9, archive.length)), length, 1521717829);
				} catch (IOException e) {
					e.printStackTrace();
					data = null;
					break;
				}

				break;
			default: // gzip
				length = stream.readInt();
				if (length <= 0 || length > 1_000_000_000) {
					data = null;
					break;
				}
				data = new byte[length];
				checkRevision(compressedLength, archive, stream.getOffset());
				if (!GZipDecompressor.decompress(stream, data)) {
					data = null;
				}
				break;
			}
		// } catch (OutOfMemoryError e) {
		// /* ignored */
		// }
	}

	private void checkRevision(int compressedLength, byte[] archive, int o) {
		InputStream stream = new InputStream(archive);
		int offset = stream.getOffset();
		if (stream.getLength() - (compressedLength + o) >= 2) {
			stream.setOffset(stream.getLength() - 2);
			revision = stream.readUnsignedShort();
			stream.setOffset(offset);
		} else {
			revision = -1;
		}

	}

	

	public Object[] editNoRevision(byte[] data, MainFile mainFile) {
		this.data = data;
		if (compression == Constants.BZIP2_COMPRESSION || compression == 3) {
			compression = Constants.GZIP_COMPRESSION;
		}
		byte[] compressed = compress();
		if (!mainFile.putArchiveData(id, compressed)) {
			return null;
		}
		return new Object[] { CRC32HGenerator.getCrc(compressed), Whirlpool.getHash(compressed, 0, compressed.length) };
	}

	public byte[][] getFiles() {
		if (data == null) {
			return null; // No data to extract
		}
	
		InputStream stream = new InputStream(data);
	
		// Read the number of files in the archive
		int fileCount = stream.readUnsignedShort();
		if (fileCount <= 0) {
			return null; // No files in this archive
		}
	
		// Read file lengths
		int[] fileLengths = new int[fileCount];
		for (int i = 0; i < fileCount; i++) {
			fileLengths[i] = stream.readInt();
		}
	
		// Extract file data
		byte[][] files = new byte[fileCount][];
		for (int i = 0; i < fileCount; i++) {
			files[i] = new byte[fileLengths[i]];
			stream.readBytes(files[i], 0, fileLengths[i]);
		}
	
		return files;
	}

	public int getId() {
		return id;
	}

	public byte[] getData() {
		return data;
	}

	public int getDecompressedLength() {
		return data.length;
	}

	public int getRevision() {
		return revision;
	}

	public void setRevision(int revision) {
		this.revision = revision;
	}

	public int getCompression() {
		return compression;
	}

	public int[] getKeys() {
		return keys;
	}

	public void setKeys(int[] keys) {
		this.keys = keys;
	}

}
