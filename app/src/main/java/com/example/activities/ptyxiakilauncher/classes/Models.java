package com.example.activities.ptyxiakilauncher.classes;

import java.io.Serializable;

public class Models {
    public static class Contact{

        private int contactID;
        private String contactName;
        private String contactNumber;

        public Contact(String contactName, String contactNumber) {
            this.contactName = contactName;
            this.contactNumber = contactNumber;
        }
        public Contact(Integer contactID, String contactName, String contactNumber) {
            this.contactID = contactID;
            this.contactName = contactName;
            this.contactNumber = contactNumber;
        }
        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public int getContactID() {
            return contactID;
        }

        public void setContactID(int contactID) {
            this.contactID = contactID;
        }
    }

    public static class FastMessage implements Serializable {
        private static final long serialVersionUID = 1L;
        private int FastMessageID;
        private String FastMessageTitle;
        private String FastMessageContent;

        public FastMessage(String fastMessageTitle, String fastMessageContent) {
            this.FastMessageTitle = fastMessageTitle;
            this.FastMessageContent = fastMessageContent;
        }
        public FastMessage(Integer fastMessageID, String fastMessageTitle, String fastMessageContent) {
            this.FastMessageID = fastMessageID;
            this.FastMessageTitle = fastMessageTitle;
            this.FastMessageContent = fastMessageContent;
        }

        public String getFastMessageContent() {
            return FastMessageContent;
        }

        public void setFastMessageContent(String fastMessageContent) {
            FastMessageContent = fastMessageContent;
        }

        public String getFastMessageTitle() {
            return FastMessageTitle;
        }

        public void setFastMessageTitle(String fastMessageTitle) {
            FastMessageTitle = fastMessageTitle;
        }

        public int getFastMessageID() {
            return FastMessageID;
        }

        public void setFastMessageID(int fastMessageID) {
            FastMessageID = fastMessageID;
        }
    }
}
