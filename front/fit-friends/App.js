/** @format */

import React, { useState } from "react";
import { View, StatusBar, StyleSheet } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import WebViewScreen from "./WebViewScreen";
import SignUpScreen from "./SignUpScreen";
import HomeScreen from "./HomeScreen";
import UserDetailScreen from "./UserDetailScreen";
import MatchListScreen from "./MatchListScreen";
import CameraScreen from "./CameraScreen";

const Stack = createStackNavigator();

export default function App() {
  return (
    <View style={styles.container}>
      <NavigationContainer>
        <Stack.Navigator initialRouteName='UserDetailScreen'>
          <Stack.Screen name='HomeScreen' component={HomeScreen} />
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
        </Stack.Navigator>
      </NavigationContainer>
      <StatusBar style='auto' />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    display: "flex",
    justifyContent: "center",
    width: "100%",
    height: "100%",
  },
});
