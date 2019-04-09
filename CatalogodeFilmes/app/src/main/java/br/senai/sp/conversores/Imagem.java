package br.senai.sp.conversores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Imagem {

    public static Bitmap arrayToBimap(byte[] imagemArray){

        Bitmap bitmap = BitmapFactory.decodeByteArray(imagemArray, 0, imagemArray.length);

        return bitmap;
    }

    public static byte[] bimapToArray(Bitmap bitmap){
        byte[] imagemArray = null;

        return  imagemArray;
    }


}
