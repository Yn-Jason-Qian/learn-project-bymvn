package net.stealthcat.test.aliyun;

import com.google.common.collect.Lists;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.Base64Utils;

import java.util.Arrays;

public class Base64Test {
    public static void main(String[] args) {
        System.out.println(new String(Base64Utils.decodeFromString("eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLXNoYW5naGFpLmFsaXl1bmNzLmNvbSIsIkJ1Y2tldCI6Im91dGluLTkzNjNjODljMGM0ODExZWE4Y2Y4MDAxNjNlMWM5NGE0IiwiRmlsZU5hbWUiOiJzdi80YWY3ODJlYy0xNzEzYTJjZDQzNS80YWY3ODJlYy0xNzEzYTJjZDQzNS5tcDQifQ")));
        System.out.println(new String(Base64Utils.decodeFromString("eyJTZWN1cml0eVRva2VuIjoiQ0FJUzBBUjFxNkZ0NUIyeWZTaklyNWZiZXQ2RG5MNXk5SktBZGsvMmpFSWhTL3NWMjQ3Q216ejJJSDFFZm5sb0FPa2FzUHd6bFd0UjZmd2VsclVxRU1RYkhoT1lOSkFwczhvTXFsajdKcGZadjh1ODRZQURpNUNqUWNJSjZLVUFtcDI4V2Y3d2FmK0FVQlhHQ1RtZDVNTVlvOWJUY1RHbFFDWnVXLy90b0pWN2I5TVJjeENsWkQ1ZGZybC9MUmRqcjhsbzF4R3pVUEcyS1V6U24zYjNCa2hsc1JZZTcyUms4dmFIeGRhQXpSRGNnVmJtcUpjU3ZKK2pDNEM4WXM5Z0c1MTlYdHlwdm9weGJiR1Q4Q05aNXo5QTlxcDlrTTQ5L2l6YzdQNlFIMzViNFJpTkw4L1o3dFFOWHdoaWZmb2JIYTlZcmZIZ21OaGx2dkRTajQzdDF5dFZPZVpjWDBha1E1dTdrdTdaSFArb0x0OGphWXZqUDNQRTNyTHBNWUx1NFQ0OFpYVVNPRHREWWNaRFVIaHJFazRSVWpYZEk2T2Y4VXJXU1FDN1dzcjIxN290ZzdGeXlrM3M4TWFIQWtXTFg3U0IyRHdFQjRjNGFFb2tWVzRSeG5lelc2VUJhUkJwYmxkN0JxNmNWNWxPZEJSWm9LK0t6UXJKVFg5RXoycExtdUQ2ZS9MT3M3b0RWSjM3V1p0S3l1aDRZNDlkNFU4clZFalBRcWl5a1QwcEZncGZUSzFSemJQbU5MS205YmFCMjUvelcrUGREZTBkc1Znb0lGS09waUdXRzNSTE5uK3p0Sjl4YmtlRStzS1VuZmVTcUpnd1FRQjI3WXdQVkZpSWVJWm1vUXcrdS9Mc3RCbksrNzYvV3l6dDVYUi91UHVncHRrUXRSQThJNjM3MmJQSTUyZVA3VWI5Ty9kcHhKM2xQMFIwV2dteWRuQkR4L1NmdTJrS3ZSaHBrUnZ2WmtaRnV3eklqRHJ1SkpGS2lhWFVuQzllZm81WG1QWEZUUW1uOGw1cEFNbXkvNjB4WHVkdmJFZVE0QUZ2SU1NQ0dvQUJJcWNuaDliNE9IMTJ2Q0ZKN1ZuZWtDRFlpRUtmUHlxTjdLSlVmSTlvczh0YVRxcEx1RnZxNFhOWEhySzJGRTVUbTZLYWdCcnMrK2w2OEpxeFdoc1kzVzZlMzJqL1YyVS9NNW05TVNzcTZhc2kyUE1qaENpWEFJSDQ0TjNHd2xTaHN4OSsvelNoYmczak5DOVpscFc4emdUVjI1TlcxNlNpd1REOFFhZnp2S0E9IiwiQWNjZXNzS2V5SWQiOiJTVFMuTlRuMWQ3cWFTQ1BrdGlHaEJ0R3Q5NEtpeSIsIkV4cGlyZVVUQ1RpbWUiOiIyMDIwLTA0LTAyVDEwOjE0OjU3WiIsIkFjY2Vzc0tleVNlY3JldCI6IkJGMkNmWWlSTGZRYVQ5bnBlRkYyVU1yTE1mS0c4dk1GUVcxUjNyNnJ3M2s2IiwiRXhwaXJhdGlvbiI6IjM1NDgiLCJSZWdpb24iOiJjbi1zaGFuZ2hhaSJ9")));
    }
}
