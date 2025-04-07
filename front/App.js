/** @format */

import React from "react";
import { View, StatusBar, StyleSheet } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import WebViewScreen from "./screens/WebViewScreen";
import SignUpScreen from "./screens/SignUpScreen";
import MatchListScreen from "./screens/MatchListScreen";
import CameraScreen from "./screens/CameraScreen";
import PostingScreen from "./screens/PostingScreen";
import BottomTabNavigator from "./navigation/BottomTabNavigator";
import Toast from "react-native-toast-message";
import UserDetailScreen from "./screens/UserDetailScreen";
const Stack = createStackNavigator();

export default function App() {
  return (
    <View style={styles.container}>
      <NavigationContainer>
        <Stack.Navigator initialRouteName='MainTabs'>
          <Stack.Screen
            name='MainTabs'
            component={BottomTabNavigator}
            options={{ headerShown: false }}
          />
          <Stack.Screen
            name='WebViewScreen'
            component={WebViewScreen}
            options={{ title: "구글 로그인" }}
          />
          <Stack.Screen
            name='CameraScreen'
            component={CameraScreen}
            options={{ title: "QR 체크" }}
          />
          <Stack.Screen
            name='SignUpScreen'
            component={SignUpScreen}
            options={{ title: "회원가입" }}
          />
          <Stack.Screen
            name='UserDetailScreen'
            component={UserDetailScreen}
            options={{ title: "회원정보" }}
          />
          <Stack.Screen
            name='MatchListScreen'
            component={MatchListScreen}
            options={{ title: "경기모집" }}
          />
          <Stack.Screen
            name='PostingScreen'
            component={PostingScreen}
            options={{ title: "게시물 작성" }}
          />
        </Stack.Navigator>
      </NavigationContainer>
      <StatusBar style='auto' />
      <Toast />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});
