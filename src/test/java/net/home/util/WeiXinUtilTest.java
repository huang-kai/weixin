package net.home.util;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;

public class WeiXinUtilTest {

    @Test
    public void testGetAccessToken() {
        String result = WeiXinUtil.getAccessToken();
        System.out.println(result);
    }

}
