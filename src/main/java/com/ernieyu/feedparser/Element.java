package com.ernieyu.feedparser;

import java.util.List;
import java.util.Set;

import org.xml.sax.Attributes;

/**
 * An XML element in the feed.
 */
public interface Element {

    /**
     * Returns the namespace URI.
     */
    String getUri();

    /**
     * Returns the element name.
     */
    String getName();
    
    /**
     * Returns the attributes associated with the element.
     */
    Attributes getAttributes();
    
    /**
     * Returns the element content.
     */
    String getContent();


    
    /**
     * Returns the first child element associated with the specified name.
     * Returns null if the element does not exist.
     */
    Element getElement(String name);
    
    /**
     * Returns a list of child elements associated with the specified name.
     * Returns an empty list if no elements are available.
     */
    List<Element> getElementList(String name);
    
    /**
     * Returns a set of keys for all child elements.  This allows an 
     * application to iterate through the elements.
     */
    Set<String> getElementKeys();
}
