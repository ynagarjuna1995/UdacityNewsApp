package io.github.ynagarjuna1995.udacitynews;

import java.util.ArrayList;


public class Article extends ArrayList<Article> {
    private String mTitle;
    private String mSectionName;
    private String mArticleLink;

    public Article(String title, String sectionName, String articleLink) {
        mTitle = title;
        mSectionName = sectionName;
        mArticleLink = articleLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getArticleLink() {
        return mArticleLink;
    }
}