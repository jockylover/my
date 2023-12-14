package com.example.myapplication.data;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.myapplication.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DataBankTest {
    DataBank dataSaverBackup;
    ArrayList<Book> shopItemsBackup;

    @Before
    public void setUp() throws Exception {
        dataSaverBackup=new DataBank();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        shopItemsBackup=dataSaverBackup.LoadBookList(targetContext);
    }

    @After
    public void tearDown() throws Exception {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dataSaverBackup.saveBookItems(targetContext,shopItemsBackup);
    }

    @Test
    public void loadBookList() {
    }

    @Test
    public void saveBookItems() {
        DataBank dataSaver=new DataBank();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ArrayList<Book> shopItems=new ArrayList<>();
        Book shopItem=new Book( R.drawable.book_1, "测试");
        shopItems.add(shopItem);
        shopItem=new Book( R.drawable.book_2,"正常");
        shopItems.add(shopItem);
        dataSaver.saveBookItems(targetContext,shopItems);

        DataBank dataLoader=new DataBank();
        ArrayList<Book> shopItemsRead=dataLoader.LoadBookList(targetContext);
        assertNotSame(shopItems,shopItemsRead);
        assertEquals(shopItems.size(),shopItemsRead.size());
        for(int index=0;index<shopItems.size();++index)
        {
            assertNotSame(shopItems.get(index),shopItemsRead.get(index));
            assertEquals(shopItems.get(index).getTitle(),shopItemsRead.get(index).getTitle());
            assertEquals(shopItems.get(index).getCoverResourceId(),shopItemsRead.get(index).getCoverResourceId());
        }
    }
}