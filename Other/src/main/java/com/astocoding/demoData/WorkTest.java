package com.astocoding.demoData;

/**
 * @author litao34
 * @ClassName WorkTest
 * @Description TODO
 * @CreateDate 2022/10/24-14:44
 **/
public class WorkTest {


    public static int versionCompare(String version1, String version2) {
        try {
            String versionArr1[] = version1.split("\\.");
            String versionArr2[] = version2.split("\\.");

            int len = Math.min(versionArr2.length, versionArr1.length);
            if (len > 0) {
                for (int i = 0; i < len; i++) {
                    if (Integer.parseInt(versionArr1[i]) == Integer.parseInt(versionArr2[i])) {
                        continue;
                    } else if (Integer.parseInt(versionArr1[i]) > Integer.parseInt(versionArr2[i])) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                //比较完成默认版本两个相等
                return 0;
            }
        } catch (Exception e) {}
        return 1;
    }

    public static void main(String[] args) {
        System.out.println(versionCompare("12.0.0","12.0"));
    }


}
