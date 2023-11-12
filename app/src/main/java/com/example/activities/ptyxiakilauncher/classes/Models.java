package com.example.activities.ptyxiakilauncher.classes;

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
}
