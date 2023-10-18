package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycle_view_books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // 获取图书列表
        List<Book> bookList = getListBooks();
        BookAdapter adapter = new BookAdapter(bookList); // 传入数据集合
        recyclerView.setAdapter(adapter);

    }

    public class Book {
        private int coverResourceId;
        private String title;

        public Book(int coverResourceId, String title) {
            this.coverResourceId = coverResourceId;
            this.title = title;
        }

        public int getCoverResourceId() {
            return coverResourceId;
        }

        public String getTitle() {
            return title;
        }
    }

    public List<Book> getListBooks() {
        List<Book> bookList = new ArrayList<>();

        // 添加图书对象到列表
        bookList.add(new Book(R.drawable.book_1, "软件项目管理案例教程（第4版）"));
        bookList.add(new Book(R.drawable.book_2, "创新工程实践"));
        bookList.add(new Book(R.drawable.book_no_name, "信息安全数学基础（第2版）"));

        return bookList;
    }

    public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
        // 在这里定义数据集合和ViewHolder

        private List<Book> bookList;

        public BookAdapter(List<Book> bookList) {
            this.bookList = bookList;
        }
        @Override
        public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // 创建并返回ViewHolder
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item_list, parent, false);
            return new BookAdapter.BookViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
            Book book = bookList.get(position);
            holder.bind(book);
        }

        @Override
        public int getItemCount() {
            // 返回数据集合的大小
            return bookList.size();
        }
        public class BookViewHolder extends RecyclerView.ViewHolder {

            // 在ViewHolder中定义视图元素

            private ImageView coverImageView;
            private TextView titleTextView;

            public BookViewHolder(@NonNull View itemView) {
                super(itemView);

                // 在构造函数中找到布局中的视图元素
                coverImageView = itemView.findViewById(R.id.image_view_book_cover);
                titleTextView = itemView.findViewById(R.id.text_view_book_title);
            }

            // 提供一个方法来绑定数据到ViewHolder中的视图元素
            public void bind(Book book) {
                coverImageView.setImageResource(book.getCoverResourceId());
                titleTextView.setText(book.getTitle());
            }

        }

    }

}