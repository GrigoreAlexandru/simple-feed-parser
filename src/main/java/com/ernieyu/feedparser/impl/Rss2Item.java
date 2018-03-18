package com.ernieyu.feedparser.impl;

import java.util.*;

import org.xml.sax.Attributes;

import com.ernieyu.feedparser.Element;
import com.ernieyu.feedparser.FeedType;
import com.ernieyu.feedparser.FeedUtils;

/**
 * Item implementation for RSS 2.0 feeds.
 */
class Rss2Item extends BaseItem {
    // XML elements for RSS items.
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String DESCRIPTION = "description";
    private static final String PUB_DATE = "pubDate";
    private static final String AUTHOR = "author";
    private static final String GUID = "guid";
    private static final String CATEGORY = "category";
    private static final String THUMBNAIL = "thumbnail";
    private static final String ENCODED = "encoded";
    private static final String CREATOR = "creator";
    
    /**
     * Constructs an Rss2Item with the specified namespace uri, name and
     * attributes.
     */
    public Rss2Item(String uri, String name, Attributes attributes) {
        super(uri, name, attributes);
    }
    
    @Override
    public FeedType getType() {
        return FeedType.RSS_2_0;
    }
    
    @Override
    public String getTitle() {
        Element title = getElement(TITLE);
        return (title != null) ? title.getContent() : null;
    }

    @Override
    public String getLink() {
        Element link = getElement(LINK);
        return (link != null) ? link.getContent() : null;
    }

    @Override
    public String getDescription() {
        Element descr = getElement(DESCRIPTION);
        return (descr != null) ? descr.getContent() : null;
    }

    @Override
    public String getAuthor() {
        Element author = getElement(CREATOR) != null ? getElement(CREATOR) :  getElement(AUTHOR) ;
        return author.getContent();
    }

    @Override
    public String getGuid() {
        Element guid = getElement(GUID);
        return (guid != null) ? guid.getContent() : null;
    }

    @Override
    public Date getPubDate() {
        Element pubDate = getElement(PUB_DATE);
        return (pubDate != null) ? FeedUtils.convertRss2Date(pubDate.getContent()) : null;
    }

    @Override
    public List<String> getCategories() {
        List<Element> elementList = getElementList(CATEGORY);
        
        // Create list of category terms.
        List<String> categories = new ArrayList<String>();
        for (Element element : elementList) {
            categories.add(element.getContent());
        }
        
        return categories;
    }

    @Override
    public String getThumbnail() {
        if (getElement(THUMBNAIL) != null) return getElement(THUMBNAIL).getAttributes().getValue("url");
        else if (getElement("content") != null) return getElement("content").getAttributes().getValue("url");
        else if (getElement("group") != null) return getElement("group").getElement("content").getAttributes().getValue(1);
        else return null;
    }

    @Override
    public String getEncoded() {
        Element encoded = getElement(ENCODED);
        return (encoded != null) ? encoded.getContent() : null;
    }


}
