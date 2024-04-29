package com.example.sonya;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";
    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private static final SecureRandom random = new SecureRandom();

    private EditText passwordLengthEditText;
    private TextView generatedPasswordTextView;
    private Button generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordLengthEditText = findViewById(R.id.password_length_edit_text);
        generatedPasswordTextView = findViewById(R.id.generated_password_text_view);
        generateButton = findViewById(R.id.generate_button);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = Integer.parseInt(passwordLengthEditText.getText().toString());
                String password = generatePassword(length);
                generatedPasswordTextView.setText(password);
            }
        });
    }

    public static String generatePassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("Password length must be at least 4 characters");
        }

        StringBuilder sb = new StringBuilder(length);
        // at least one lowercase letter
        sb.append(CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length())));
        // at least one uppercase letter
        sb.append(CHAR_UPPER.charAt(random.nextInt(CHAR_UPPER.length())));
        // at least one digit
        sb.append(NUMBER.charAt(random.nextInt(NUMBER.length())));
        // at least one special character
        sb.append(OTHER_CHAR.charAt(random.nextInt(OTHER_CHAR.length())));

        for (int i = 4; i < length; i++) {
            sb.append(PASSWORD_ALLOW_BASE.charAt(random.nextInt(PASSWORD_ALLOW_BASE.length())));
        }

        return shuffleString(sb.toString());
    }

    // Fisher–Yates shuffle
    private static String shuffleString(String string) {
        char[] charArray = string.toCharArray();
        for (int i = charArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = charArray[index];
            charArray[index] = charArray[i];
            charArray[i] = temp;
        }
        return new String(charArray);
    }
}
