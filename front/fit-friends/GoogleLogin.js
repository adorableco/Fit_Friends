/** @format */

import * as React from "react";
import * as WebBrowser from "expo-web-browser";
import * as Google from "expo-auth-session/providers/google";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { Button, View, Text, StyleSheet, TouchableOpacity } from "react-native";
import { useEffect } from "react";
import { createStackNavigator } from "@react-navigation/stack";
import axios from "axios";

// 로그인 버튼 누르면 웹 브라우저가 열리고, 구글 로그인 페이지로 이동함.
WebBrowser.maybeCompleteAuthSession();
export default function GoogleLogin({ navigation }) {
  const Stack = createStackNavigator();
  // 안드로이드, 웹 클라이언트 아이디를 사용하여 인증 요청 보냄.
  // Google 인증 요청을 위한 훅 초기화
  // promptAsync: 인증 요청 보냄.
  const [request, response, promptAsync] = Google.useAuthRequest({
    webClientId: EXPO_PUBLIC_WEB_CLIENT_ID,
    androidClientId: EXPO_PUBLIC_ANDROID_CLIENT_ID,
  });

  // Google 로그인 처리하는 함수
  const handleSignInWithGoogle = async () => {
    const user = await AsyncStorage.getItem("@accessToken");
    if (!user) {
      if (response?.type === "success") {
        await sendToken(response.authentication?.accessToken);
      }
    }
  };

  //구글로그인을 해서 받은 토큰을 백엔드로 보내서 디비에 있는 회원 내용 조회 예정
  const sendToken = async (token) => {
    await axios
      .get(`http://fit-friends.duckdns.org:8081/api/login/${token}`)
      .then((res) => {
        if (res.data.accessToken == null) {
          navigation.navigate("SignUpScreen", { userData: res.data });
        } else {
          AsyncStorage.setItem("@accessToken", res.data.accessToken);
          AsyncStorage.setItem("@userId", res.data.userId);
          console.log(res.data.userId);
          navigation.navigate("UserDetailScreen", { userId: res.data.userId });
        }
      });
  };

  const handleLogout = async () => {
    await AsyncStorage.removeItem("@accessToken");
    // await AsyncStorage.removeItem("@userId");
  };

  // Google 인증 응답이 바뀔때마다 실행
  useEffect(() => {
    handleSignInWithGoogle();
  }, [response]);

  return (
    <View>
      <TouchableOpacity
        style={[styles.buttonStyle, { marginBottom: 18 }]}
        disabled={!request}
        title='Login'
        onPress={() => {
          promptAsync();
        }}
      >
        <Text style={styles.buttonTextStyle}>로그인하여 시작하기</Text>
      </TouchableOpacity>

      <Button title='logout' onPress={() => handleLogout()} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  textContainer: {
    marginTop: 130,
    justifyContent: "flex-start",
    alignItems: "flex-start",
    paddingLeft: 30,
  },
  imageContainer: {
    marginBottom: 51,
    justifyContent: "flex-start",
    alignItems: "center",
    marginTop: 70,
  },
  subtitleTextStyle: {
    color: "#4CAF50",
    fontFamily: "Roboto",
    fontSize: 24,
    fontWeight: "400",
    lineHeight: 28,
    flexShrink: 0,
  },
  titleTextStyle: {
    color: "#4CAF50",
    fontFamily: "Roboto",
    fontSize: 64,
    fontWeight: "700",
    lineHeight: 68,
    flexShrink: 0,
  },
  buttonTextStyle: {
    color: "#FFFFFF",
    fontFamily: "Roboto",
    fontSize: 16,
    fontWeight: "400",
    lineHeight: 20,
    flexShrink: 0,
  },
  buttonStyle: {
    width: 195,
    height: 48,
    borderRadius: 50,
    backgroundColor: "#4CAF50",
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "row",
    flexShrink: 0,
  },
  buttonImageStyle: {
    marginRight: 10,
  },
  buttonTextStyle: {
    color: "white",
    fontSize: 16,
  },
  imageStyle: {
    width: 306,
    height: 316,
    flexShrink: 0,
  },
});
