package com.ernieyu.feedparser.impl;

import java.util.*;

import org.xml.sax.Attributes;

import com.ernieyu.feedparser.Element;
import com.ernieyu.feedparser.Feed;
import com.ernieyu.feedparser.FeedType;
import com.ernieyu.feedparser.FeedUtils;
import com.ernieyu.feedparser.Item;

/**
 * Feed implementation for Atom 1.0.
 */
class AtomFeed extends BaseElement implements Feed {
    // XML elements for Atom feeds.
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String SUB_TITLE = "subtitle";
    private static final String UPDATED = "updated";
    private static final String RIGHTS = "rights";
    private static final String CATEGORY = "category";
    private static final String ENTRY = "entry";
	
	/**
	 * Constructs an AtomFeed with the specified namespace uri, name and 
	 * attributes.
	 */
	public AtomFeed(String uri, String name, Attributes attributes) {
	    super(uri, name, attributes);
	}
	
    @Override
	public FeedType getType() {
		return FeedType.ATOM_1_0;
	}
	
    @Override
	public String getTitle() {
	    Element title = getElement(TITLE);
	    return (title != null) ? title.getContent() : null;
	}
	
    @Override
	public String getLink() {
        Element link = getElement(LINK);
        return (link != null) ? link.getAttributes().getValue("href") : null;
	}
	
    @Override
	public String getDescription() {
        Element descr = getElement(SUB_TITLE);
        return (descr != null) ? descr.getContent() : null;
	}

    @Override
    public String getLanguage() {
        // Not implemented.  Atom language is specified using the xml:lang
        // attribute on elements.
        return null;
    }
    
    @Override
    public String getCopyright() {
        Element rights = getElement(RIGHTS);
        return (rights != null) ? rights.getContent() : null;
    }
    
    @Override
    public Date getPubDate() {
        Element pubDate = getElement(UPDATED);
        return (pubDate != null) ? FeedUtils.convertAtomDate(pubDate.getContent()) : null;
    }

    @Override
    public List<String> getCategories() {
        List<Element> elementList = getElementList(CATEGORY);
        
        // Create list of category terms.
        List<String> categories = new ArrayList<String>();
        for (Element element : elementList) {
            categories.add(element.getAttributes().getValue("term"));
        }
        
        return categories;
    }
	
	@Override
	public List<Item> getItemList() {
        // Get element list for entries.
	    List<Element> elementList = getElementList(ENTRY);
        List<Item> itemList = new ArrayList<Item>();
	    
        // Build item list.
        if (elementList != null) {
            for (Element element : elementList) {
                itemList.add((Item) element);
            }
        }
	    
	    return itemList;
	}
    
    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public Map<String, String> getCloud() {
        Element el = getElement("channel");
        HashMap<String, String> map = null;
        Element ele;
        Attributes atr;
        if (el != null && (ele = el.getElement("cloud")) != null && (atr = ele.getAttributes()) != null){
            map = new HashMap<String, String>();
            map.put("domain", atr.getValue("domain"));
            map.put("port", atr.getValue("port"));
            map.put("path", atr.getValue("path"));
            map.put("registerProcedure", atr.getValue("registerProcedure"));
            map.put("protocol", atr.getValue("protocol"));
        }
        return map;
    }

    @Override
    public String getWebSub() {
	    Element element = getElement("channel");
        List<Element> list;
        if ( element != null && (list = element.getElementList("link")) != null) {
            for (Element el : list) {
                Attributes atr = el.getAttributes();
                String val = atr.getValue("rel");
                if (val != null && val.equals("hub")) return atr.getValue("href");
            }
        }
        return null;
    }
}
