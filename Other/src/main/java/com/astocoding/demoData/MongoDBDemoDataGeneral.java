package com.astocoding.demoData;

import lombok.Builder;
import lombok.Data;

import javax.management.relation.RelationNotFoundException;
import javax.xml.ws.BindingType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author litao34
 * @ClassName MongoDBDemoDataGeneral
 * @Description TODO
 * @CreateDate 2022/9/28-16:04
 **/
public class MongoDBDemoDataGeneral {
    private static List<String> nameCollection = Arrays.asList("Tim","Dave","Stuart","Mark");
//            "Phil","Jerry","Kevin", "Jon","Bob","Carl","Mel");

    private static List<String> tags = Arrays.asList("MongoDB","Mysql","Java","C++");
//    ,"Golang","Redis","Spring","RocketMQ");

    private static Integer generalCount = 20;

    private static Random randomHanler = new Random();

    public static void main(String[] args) {
        for (int i = 0 ; i < generalCount ; i++){
            String name,title;
            PageDemo build = PageDemo.builder()
                    .author(name = randomAuthor())
                    .like( Math.abs(randomHanler.nextInt()) % 300)
                    .tags(getRandomTagList())
                    .title(title = "[" + i + "]the page title of " + name)
                    .url("http://astocoding.com/" + title + "/index")
                    .build();
            System.out.printf(build + "\n");
        }
    }

    private static List<String> getRandomTagList() {
        Integer tagCount = Math.abs(randomHanler.nextInt()) % 4 + 1;
        List<String> result = new ArrayList<>();
        for (int i=0;i<tagCount;i++){
            Integer randomIndex = Math.abs(randomHanler.nextInt()) % tags.size();
            if (!result.contains(tags.get(randomIndex))){
                result.add(tags.get(randomIndex));
            }
        }
        return result;
    }

    private static String randomAuthor() {
        Integer randomNameIndex = Math.abs(randomHanler.nextInt()) % nameCollection.size();
        return nameCollection.get(randomNameIndex);
    }


}

@Data
@Builder
class PageDemo{
    private String author;
    private Integer like;
    private String title;
    private String url;
    private List<String> tags;

    public String toString(){
        String result = "{\n" +
                "\tauthor:\"" + author + "\",\n" +
                "\tlike:" + like + ",\n" +
                "\ttitle:\"" + title + "\",\n" +
                "\turl:\"" + url + "\",\n" +
                "\ttags:[" +getTagsString()+ "]\n" +
                "},";
        return result;
    }

    private String getTagsString() {
        StringBuilder stringBuilder = new StringBuilder();
        Boolean first = true;
        for (String tag : tags) {
            if (first){
                stringBuilder.append("\"" + tag +"\"");
                first = false;
            }else{
                stringBuilder.append(",\""+tag + "\"");
            }
        }
        return stringBuilder.toString();
    }
}
