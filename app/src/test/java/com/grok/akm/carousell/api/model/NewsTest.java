package com.grok.akm.carousell.api.model;

import com.grok.akm.carousell.Utils.Constants;
import com.grok.akm.carousell.model.News;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class NewsTest {

    private final String id = "121";
    private final String title = "Carousell";
    private final String description = "Carousell News";
    private final String bannerUrl = "https://storage.googleapis.com/";
    private final int timeCreated = 1532853058;
    private final int rank = 7;
    private final String date = "2018-03-27 8:30:00";

    @Mock
    News news;

    @Before
    public void setUp() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

        MockitoAnnotations.initMocks(this);
        Mockito.when(news.getId()).thenReturn(id);
        Mockito.when(news.getTitle()).thenReturn(title);
        Mockito.when(news.getDescription()).thenReturn(description);
        Mockito.when(news.getBannerUrl()).thenReturn(bannerUrl);
        Mockito.when(news.getTimeStamp()).thenReturn(timeCreated);
        Mockito.when(news.getRank()).thenReturn(rank);
        Mockito.when(news.getDate()).thenReturn(sdf.parse(date));
    }

    @Test
    public void testNewsId(){
        Mockito.when(news.getId()).thenReturn(id);
        Assert.assertEquals("121",news.getId());
    }

    @Test
    public void testNewsTitle(){
        Mockito.when(news.getTitle()).thenReturn(title);
        Assert.assertEquals("Carousell",news.getTitle());
    }

    @Test
    public void testNewsDescription(){
        Mockito.when(news.getDescription()).thenReturn(description);
        Assert.assertEquals("Carousell News",news.getDescription());
    }

    @Test
    public void testNewsBannerUrl(){
        Mockito.when(news.getBannerUrl()).thenReturn(bannerUrl);
        Assert.assertEquals("https://storage.googleapis.com/",news.getBannerUrl());
    }

    @Test
    public void testNewsTimeCreated(){
        Mockito.when(news.getTimeStamp()).thenReturn(timeCreated);
        Assert.assertEquals(1532853058,news.getTimeStamp());
    }

    @Test
    public void testNewsRank(){
        Mockito.when(news.getRank()).thenReturn(rank);
        Assert.assertEquals(7,news.getRank());
    }

    @Test
    public void testNewsDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATETIME_FORMAT, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        Mockito.when(news.getDate()).thenReturn(sdf.parse(date));
        Assert.assertEquals(sdf.parse(date),news.getDate());
    }

}
