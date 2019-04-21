package com.example.dilocker;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        PDFView pdfView = findViewById(R.id.pdfView);
        String pdfUri = (getIntent().getStringExtra("PDF_URI"));

        pdfView.fromUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/dilocker-da889.appspot.com/o/postPdf%2F1555435157780.pdf?alt=media&token=1027f993-2932-47eb-9e48-70c706815716")).load();
    }
}
