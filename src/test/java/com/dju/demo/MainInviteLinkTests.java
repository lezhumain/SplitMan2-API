package com.dju.demo;

import com.dju.demo.helpers.InviteLinkData;
import com.dju.demo.helpers.InviteLinkHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class MainInviteLinkTests {
    public MainInviteLinkTests() {
    }

    @Test
    void checkEncrypt () throws Exception {
        final InviteLinkData initial = new InviteLinkData(1, 83);

        final String myLink = InviteLinkHelper.generateLink(initial);
        Assertions.assertNotEquals(myLink, "");

        final InviteLinkData ild = InviteLinkHelper.parseLink(myLink);
        Assertions.assertNotNull(ild);

        Assertions.assertEquals(ild, initial);
    }
}
