package com.teamtreehouse.mememaker.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.teamtreehouse.mememaker.MemeMakerApplication;
import com.teamtreehouse.mememaker.MemeMakerApplicationSettings;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Evan Anger on 7/28/14.
 */
public class FileUtilities {

    public static void saveAssetImage(Context context, String assetName) {

        //Create a file handle to use methods to handle files
        File fileDirectory = getFileDirectory(context);
        //Create file inside fileDirectory named assetName
        File fileToWrite = new File(fileDirectory, assetName);



        AssetManager assetManager = context.getAssets();

        //Create file streams to read and write
        try {
            InputStream in = assetManager.open(assetName);
            FileOutputStream out = new FileOutputStream(fileToWrite);
            copyFile(in, out);

            //Close input and output streams
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //refactor method
    public static File getFileDirectory(Context context) {
        MemeMakerApplicationSettings settings = new MemeMakerApplicationSettings(context);
        String storageType = settings.getStoragePreference();

        //Only if
        if(storageType.equals(StorageType.INTERNAL)) {

            //return internal storage directory
            return context.getFilesDir();
        }
        else {
            if(isExternalStorageAvailable()) {

                if(storageType.equals(StorageType.PRIVATE_EXTERNAL)) {
                    //private external storage
                    return context.getExternalFilesDir(null);
                }
                else {
                    //public external storage
                    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                }
            }
            else {
                return context.getFilesDir();
            }
        }



    }

    public static boolean isExternalStorageAvailable() {
        //Get state of storage
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {

            //External storage is available on device
            return true;
        }
        return false;

    }

    //Reusable copy file method from input stream to output stream
    private static void copyFile(InputStream in, OutputStream out) throws IOException {

        //Read from the input stream to write to the outputstream

        //Create 1 byte buffer
        byte[] buffer = new byte[1024];
        int read;

        //While there is data of one byte in the inputStream
        while((read = in.read(buffer)) != -1) {
            //Write to the outputStream the buffer and the data
            out.write(buffer, 0, read);
        }
    }

    public static File[] listFiles(Context context) {
        File fileDirectory = getFileDirectory(context);
        //Get and filter only jpg files
        File[] filteredFiles = fileDirectory.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                if (file.getAbsolutePath().contains(".jpg")) {
                    return true;
                }
                else {
                    return false;
                }

            }
        });

        return filteredFiles;
    }

    public static Uri saveImageForSharing(Context context, Bitmap bitmap,  String assetName) {
        File fileToWrite = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), assetName);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return Uri.fromFile(fileToWrite);
        }
    }


    public static void saveImage(Context context, Bitmap bitmap, String name) {
        File fileDirectory = getFileDirectory(context);
        File fileToWrite = new File(fileDirectory, name);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
