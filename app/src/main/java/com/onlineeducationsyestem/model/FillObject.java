package com.onlineeducationsyestem.model;

public class FillObject {
    public String text;
        public Boolean isTextView;
        public int lineNumber;

        public FillObject(String text, Boolean isTextView, int lineNumber) {
            this.text = text;
            this.isTextView = isTextView;
            this.lineNumber = lineNumber;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Boolean getTextView() {
            return isTextView;
        }

        public void setTextView(Boolean textView) {
            isTextView = textView;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }
}