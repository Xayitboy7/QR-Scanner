package com.example.qrscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    Button scan_btn;
    TextView textView;
    ImageButton img_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // Вызов метода суперкласса для базовой инициализации
        setContentView(R.layout.activity_main);  // Установка макета для текущей активности

        // Инициализация кнопки сканирования, текстового поля и кнопки с изображением
        scan_btn = findViewById(R.id.Scanner);
//        textView = findViewById(R.id.text);  // Инициализация textView
        img_btn = findViewById(R.id.imageButton);

        // Создаем общий обработчик для обеих кнопок
        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Создаем и запускаем интегратор для сканирования QR-кода
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);

                // Блокировка ориентации экрана во время сканирования
                intentIntegrator.setOrientationLocked(true);

                // Устанавливаем текстовое сообщение во время сканирования
                intentIntegrator.setPrompt("Scan a QR Code");

                // Указываем, что нужно сканировать только QR-коды
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

                // Инициализация процесса сканирования
                intentIntegrator.initiateScan();
            }
        };

        // Устанавливаем слушатель нажатия для обеих кнопок
        scan_btn.setOnClickListener(buttonClickListener);
        img_btn.setOnClickListener(buttonClickListener);  // Оба выполняют одно и то же действие
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Обрабатываем результат сканирования QR-кода
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        // Проверяем, удалось ли получить данные из результата
        if (intentResult != null) {
            String contents = intentResult.getContents();

            // Если данные существуют (QR-код был успешно отсканирован)
            if (contents != null) {
                // Устанавливаем текстовое поле на содержимое QR-кода
                textView.setText(intentResult.getContents());
            }
        } else {
            // Если результат был пустой, вызываем стандартный метод родителя
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
