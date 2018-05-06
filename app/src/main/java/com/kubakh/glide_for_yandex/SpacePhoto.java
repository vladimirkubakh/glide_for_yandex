package com.kubakh.glide_for_yandex;

import android.os.Parcel;
import android.os.Parcelable;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


/*
Общение между активити
 */
public class SpacePhoto implements Parcelable {
    private String mUrl;
    private String mTitle;
    public static volatile Document doc = null;
    public SpacePhoto(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected SpacePhoto(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }
    //Генерация объекта передатчика
    public static final Creator<SpacePhoto> CREATOR = new Creator<SpacePhoto>() {
        @Override
        public SpacePhoto createFromParcel(Parcel in) {
            return new SpacePhoto(in);
        }

        @Override
        public SpacePhoto[] newArray(int size) {
            return new SpacePhoto[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
    //Создание объектов SpacePhoto с адресами нужным картинок
    public static SpacePhoto[] getSpacePhotos() {
        //Поток парсера, чтобы выгрузить документ с адресами
        Thread loadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    doc = Jsoup.connect("https://wallpaperscraft.ru").get();
                    String title = doc.title();
                    builder.append(title).append("\n");
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }
            }
        });
        loadThread.start();
        do
        {
            try{
                loadThread.join(250);
            }catch(InterruptedException e){}
        }
        while(loadThread.isAlive());
        //Парсим документ и вытаскиваем адреса
        Elements links = doc.select(".wallpapers__image");
        int imagesCount = links.size();
        SpacePhoto[] photos = new SpacePhoto[imagesCount];
        for(int i=0; i<imagesCount; ++i) {
            photos[i]= new SpacePhoto(links.get(i).attr("src"), links.get(i).attr("alt"));
        }
        return photos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}
