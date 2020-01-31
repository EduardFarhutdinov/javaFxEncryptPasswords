package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordGenerator {

    private static final String UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";
    private boolean useUpper;
    private boolean usePunctuation;

    private PasswordGenerator() {
        throw new UnsupportedOperationException("Empty constructor is not supported.");
    }

    private PasswordGenerator(PasswordGeneratorBuilder builder) {
        this.useUpper = builder.useUpper;
        this.usePunctuation = builder.usePunctuation;
    }

    public static class PasswordGeneratorBuilder {

        private boolean useLower;
        private boolean useUpper;
        private boolean useDigits;
        private boolean usePunctuation;

        public PasswordGeneratorBuilder() {
            this.useUpper = false;
            this.usePunctuation = false;
        }


        public PasswordGeneratorBuilder useLower(boolean useLower) {
            this.useLower = useLower;
            return this;
        }


        public PasswordGeneratorBuilder useUpper(boolean useUpper) {
            this.useUpper = useUpper;
            return this;
        }


        public PasswordGeneratorBuilder useDigits(boolean useDigits) {
            this.useDigits = useDigits;
            return this;
        }


        public PasswordGeneratorBuilder usePunctuation(boolean usePunctuation) {
            this.usePunctuation = usePunctuation;
            return this;
        }


        public PasswordGenerator build() {
            return new PasswordGenerator(this);
        }
    }

    ObservableList<String> passwords = FXCollections.observableArrayList();
    public ObservableList<String> generate(int length, int count) {
        // Argument Validation.
        if (length <= 0) {
            return null;
        }

        // Variables.
        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());

        List<String> charCategories = new ArrayList<>(4);
//        List<String> passwords = new ArrayList<>();

        if (useUpper) {
            charCategories.add(UPPER);
        }

        if (usePunctuation) {
            charCategories.add(PUNCTUATION);
        }

        for (int j = count; j > 0; j--) {
            for (int i = 0; i < length; i++) {
                String charCategory = charCategories.get(random.nextInt(charCategories.size()));
                int position = random.nextInt(charCategory.length());
                password.append(charCategory.charAt(position));
            }
            passwords.add(String.valueOf(password));
            password.setLength(0);
        }
        return passwords;
    }

    @Override
    public String toString() {
        String e1 = null ;
        for (String element:passwords) {
            e1 = element;
        }
        return  e1 + " ";
    }
}