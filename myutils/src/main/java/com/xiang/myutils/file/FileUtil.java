package com.xiang.myutils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileUtil {
	public static final String CONFIG_PATH = "config";
	public static final String DOWNLOAD_PATH = "download";
	public static final String FILE_ADDRESS = "address";

	// public static String getPath(Context context){
	//
	// if (Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
	// return Environment.getExternalStorageDirectory().getAbsolutePath() +
	// File.separator + "WlanTool"+ File.separator ;
	// } else {// 如果SD卡不存在，就保存到本应用的目录下
	// return context.getFilesDir().getAbsolutePath()+ File.separator +
	// "WlanTool"+ File.separator ;
	// }
	// }

	/**
	 * 获取sd卡目录，如果没有sd卡，则返回应用缓存目录
	 * 
	 * @param context
	 * @param pathName
	 * @return
	 */
	public static String getCachePath(Context context, String pathName) {
		String path = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File sd = Environment.getExternalStorageDirectory();
			path = sd.getPath() + File.separator + context.getPackageName()
					+ File.separator + pathName;
		} else {
			File cache = context.getCacheDir();
			path = cache.getPath() + File.separator + pathName;
		}
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		return path;
	}

	public static String getMadeTextModelPath(Context context) {

		return getCachePath(context, "yome/image/TextModel");

	}

	public static String getWorkPath(Context context) {

		return getCachePath(context, "yome/image/Works");

	}

	public static String getCrashPath(Context context) {

		return getCachePath(context, "yome/crash");

	}

	/**
	 * 根据路径删除文件
	 * 
	 * @param path
	 */
	public static void deleteTempFile(String path) {
		if (path != null) {
			deleteTempFile(new File(path));
		}
	}

	public static void deleteTempFile(File file) {
		if (file != null && file.exists()) {
			file.delete();
		}
	}

	/**
	 * 获取保存图片的目录
	 * 
	 * @return
	 */
	public static File getAlbumDir(Context context) {
		File dir = new File(getCachePath(context, getAlbumName()));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 获取保存 隐患检查的图片文件夹名称
	 * 
	 * @return
	 */
	public static String getAlbumName() {
		return "yome";
	}

	/**
	 * 写入文件
	 * 
	 * @param list
	 * @param path
	 * @param fileName
	 */
	public static void writeObject(Object object, String path, String fileName) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(path
					+ File.separator + fileName));
			out.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// close
		if (null != out) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeObject(Object[] object, String path, String fileName) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(path
					+ File.separator + fileName));
			for (int i = 0; i < object.length; i++)
				out.writeObject(object[i]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// close
		if (null != out) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static Object readObject(String path, String fileName) {
		ObjectInputStream in = null;
		Object object = null;
		try {
			in = new ObjectInputStream(new FileInputStream(path
					+ File.separator + fileName));
			object = (Object) in.readObject();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (null != in) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	public static void readObjects(Object[] objects, String path,
			String fileName) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(path
					+ File.separator + fileName));
			for (int i = 0; i < objects.length; i++)
				objects[i] = (Object) in.readObject();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (null != in) {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void write(String text, String path, String fileName) {
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(path + fileName),
					"utf-8");
			osw.write(text);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static String read(String path, String fileName) {
		StringBuffer buffer = new StringBuffer();
		InputStreamReader isr = null;
		Reader in = null;
		try {
			FileInputStream fis = new FileInputStream(path + fileName);
			isr = new InputStreamReader(fis, "utf-8");
			in = new BufferedReader(isr);
			int ch;
			while ((ch = in.read()) > -1) {
				buffer.append((char) ch);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return buffer.toString();
	}

	public static File write2SDFromInput(String path, String fileName,
			InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			// createSDDir(path);
			file = new File(path + File.separator + fileName);
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(input, "utf-8"));
			StringBuffer strBuffer = new StringBuffer();
			String data = "";
			while ((data = bufferReader.readLine()) != null) {
				strBuffer.append(data + "\n");
			}
			output = new FileOutputStream(file);
			Writer out = new OutputStreamWriter(output, "utf-8");
			out.write(strBuffer.toString());
			out.close();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static String getStrFromSD(Context context, String path,
			String filename) {
		String content = null;
		InputStream instream = null;
		try {
			instream = new FileInputStream(new File(path + File.separator
					+ filename));
			InputStreamReader inputreader = new InputStreamReader(instream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			String line;
			while ((line = buffreader.readLine()) != null) {
				content += line + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != instream) {
				try {
					instream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return content;
	}

	/**
	 * 清空文件
	 * 
	 * @param list
	 * @param path
	 * @param fileName
	 */
	public static void clearObject(String path, String fileName) {

		try {
			File file = new File(path + File.separator + fileName);
			if (file.exists()) {
				boolean isdelte = file.delete();
				Log.i("file", "del success:");
			}
			// String p = path + File.separator + fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readTextFile(File file) throws IOException {
		String text = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			text = readTextInputStream(is);
			;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return text;
	}

	public static String readLocalJson(Context context, String fileName) {
		String jsonString = "";
		String resultString = "";
		try {
			InputStream inputStream = context.getResources().getAssets()
					.open(fileName);
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			resultString = new String(buffer, "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return resultString;
	}

	/**
	 * 从流中读取文件
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readTextInputStream(InputStream is) throws IOException {
		StringBuffer strbuffer = new StringBuffer();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				strbuffer.append(line).append("\r\n");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return strbuffer.toString();
	}

	// public static boolean writeFile(StringBuffer sb,String fileName,String
	// path) {
	// String string;
	// DataOutputStream bfo = null;
	// File fileDir = new File(path);
	// if(!fileDir.exists())
	// fileDir.mkdirs();
	//
	// try {
	// string = sb.substring(0);
	// bfo = new DataOutputStream(new FileOutputStream(path+fileName));
	// bfo.write(string.getBytes("gbk"));
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return true;
	// }
	//
	// public static String readFile(File file) throws Exception
	// {
	// StringBuffer sb = new StringBuffer();
	// try {
	// FileInputStream fis = new FileInputStream(file);
	// BufferedReader br = new BufferedReader(new
	// InputStreamReader(fis,"UTF-8"));
	// String line = null;
	// while ((line = br.readLine()) != null) {
	// sb.append(line);
	// }
	// fis.close();
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// if(sb.toString().trim().length()<=0) return null;
	// return sb.toString();
	// }
}
