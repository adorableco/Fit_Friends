/** @format */

import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Ionicons } from "@expo/vector-icons";

// 스크린 임포트
import HomeScreen from "../screens/HomeScreen";
import UserDetailScreen from "../screens/UserDetailScreen";
import MatchListScreen from "../screens/MatchListScreen";

const Tab = createBottomTabNavigator();

export default function BottomTabNavigator() {
  return (
    <Tab.Navigator
      screenOptions={({ route }) => ({
        tabBarIcon: ({ focused, color, size }) => {
          let iconName;

          if (route.name === "홈") {
            iconName = focused ? "home" : "home-outline";
          } else if (route.name === "프로필") {
            iconName = focused ? "person" : "person-outline";
          } else if (route.name === "모집글") {
            iconName = focused ? "list" : "list-outline";
          }

          return <Ionicons name={iconName} size={size} color={color} />;
        },
        tabBarActiveTintColor: "#4CAF50",
        tabBarInactiveTintColor: "gray",
        headerShown: false,
      })}
    >
      <Tab.Screen name='홈' component={HomeScreen} />
      <Tab.Screen name='모집글' component={MatchListScreen} />
      <Tab.Screen name='프로필' component={UserDetailScreen} />
    </Tab.Navigator>
  );
}
