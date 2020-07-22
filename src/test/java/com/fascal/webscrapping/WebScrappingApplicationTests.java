package com.fascal.webscrapping;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
class WebScrappingApplicationTests {

    @Test
    void contextLoads() throws IOException {
        String url = "https://www.cermati.com/artikel";
        Document document = Jsoup.connect(url).get();
        for (Element listOfArticle : document.select("div.list-of-articles")) {
            Elements listPathElement = listOfArticle.getElementsByAttributeValueMatching("itemprop", "url");
            for (Element pathElement : listPathElement) {
                String pathElementValue = pathElement.attr("href");
                url = url.replace("/artikel", "") + pathElementValue;
                log.info("URL : " + url);

                Document documentDetail = Jsoup.connect(url).get();
                Elements elementTitle = documentDetail.getElementsByAttributeValueMatching("itemprop", "headline");
                for (Element titleObject : elementTitle) {
                    log.info("Title : " + titleObject.html());
                }
                Elements elementAuthor = documentDetail.getElementsByAttributeValueMatching("itemprop", "author");
                for (Element authorObject : elementAuthor) {
                    log.info("setAuthor : " + authorObject.html());
                }

                Elements elementPostDateList = documentDetail.getElementsByAttributeValueMatching("itemprop", "datePublished");
                for (Element elemetPostDate : elementPostDateList) {
                    log.info("Date Posting : " + elemetPostDate.html());
                }

                getRelatedArticle(documentDetail);

            }
        }
    }

    private void getRelatedArticle(Document documentDetail) {
        Elements elementArtikelTerkait = documentDetail.select("ul.panel-items-list");
        for (Element elementTerkait : elementArtikelTerkait) {
            Elements listUrlElement = elementTerkait.select("li>a");
            for (Element urlElement : listUrlElement) {
                String urlValue = urlElement.attr("href");
                log.info("Sub Url : " + urlValue);
                Elements listElementTitle = urlElement.select("a>h5.item-title");
                for (Element titleElement : listElementTitle) {
                    String title = titleElement.html();
                    log.info("Subtitle : " + title);
                }
            }
        }
    }

}
