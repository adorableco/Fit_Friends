/** @format */

import React, { useState } from "react";
import { View, StatusBar, StyleSheet } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import WebViewScreen from "./WebViewScreen";
import SignUpScreen from "./SignUpScreen";
import HomeScreen from "./HomeScreen";

const Stack = createStackNavigator();

export default function App() {
  return (
    <View style={styles.container}>
      <NavigationContainer>
        <Stack.Navigator initialRouteName='HomeScreen'>
          <Stack.Screen name='HomeScreen' component={HomeScreen} />
          <Stack.Screen
            name='WebViewScreen'
            component={WebViewScreen}
            options={{ title: "구글 로그인" }}
          />
          <Stack.Screen
            name='SignUpScreen'
            component={SignUpScreen}
            options={{ title: "회원가입" }}
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
    fontFamily: "Kim jung chul gothic",
    display: "flex",
    justifyContent: "center",
  },
});
