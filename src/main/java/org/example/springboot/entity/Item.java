package org.example.springboot.entity;

public class Item {

        private Integer itemId;
        private String itemName;
        private Integer categoryId;



        private String categoryName;
        private String location;
        private String locationName;
        private String status;
        private String occurDate;
        private String uniqueMark;
        private String description;
        private String imageUrl;
        private Integer userId;
        private String posterWhatsapp;
        private String publishTime;
        private String intent;

        public Integer getItemId() { return itemId; }
        public void setItemId(Integer itemId) { this.itemId = itemId; }
        public String getItemName() { return itemName; }
        public void setItemName(String itemName) { this.itemName = itemName; }
        public Integer getCategoryId() { return categoryId; }
        public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
        public String getCategoryName() { return categoryName; }
        public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public String getLocationName() { return locationName; }
        public void setLocationName(String locationName) { this.locationName = locationName; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getOccurDate() { return occurDate; }
        public void setOccurDate(String occurDate) { this.occurDate = occurDate; }
        public String getUniqueMark() { return uniqueMark; }
        public void setUniqueMark(String uniqueMark) { this.uniqueMark = uniqueMark; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public Integer getUserId() { return userId; }
        public void setUserId(Integer userId) { this.userId = userId; }
        public String getPosterWhatsapp() { return posterWhatsapp; }
        public void setPosterWhatsapp(String posterWhatsapp) { this.posterWhatsapp = posterWhatsapp; }
        public String getPublishTime() { return publishTime; }
        public void setPublishTime(String publishTime) { this.publishTime = publishTime; }
        public String getIntent() {
                return intent;
        }

        public void setIntent(String intent) {
                this.intent = intent;
        }
    }

