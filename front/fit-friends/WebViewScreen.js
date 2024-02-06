/** @format */

import React, { useEffect, useState } from "react";
import { WebView } from "react-native-webview";
import axios from "axios";
import { useNavigation } from "@react-navigation/native";
import queryString from "query-string";

const WebViewScreen = () => {
  const [url, setUrl] = useState(null);
  const [accessToken, setAccessToken] = useState(null);
  const [name, email, picture] = useState(null);
  const navigation = useNavigation();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          "http://fit-friends.duckdns.org:8081/api/login",
        );
        setUrl(response.data);
        setAccessToken(response.data.accessToken);
      } catch (error) {
        console.error("Failed to fetch data:", error);
      }
    };

    fetchData();
  }, []);

  const handleNavigationStateChange = (navState) => {
    console.log("accessToken:", accessToken);
    console.log("navState:", navState);

    if (accessToken == null) {
      // URL에서 name, email, picture를 파싱합니다.
      const urlParams = queryString.parse(navState.url.split("?")[1]);
      const name = urlParams.name;
      const email = urlParams.email;
      const picture = urlParams.picture;

      // 파싱한 name, email, picture를 SignUpScreen으로 전달합니다.
      navigation.navigate("SignUpScreen", {
        name: name,
        email: email,
        picture: picture,
      });
    }
  };

  return url ? (
    <WebView
      source={{ uri: url }}
      onNavigationStateChange={handleNavigationStateChange}
      userAgent='Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36'
      style={{ flex: 1 }}
    />
  ) : null;
};

export default WebViewScreen;
