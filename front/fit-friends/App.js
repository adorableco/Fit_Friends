import React from 'react';
import { StatusBar } from 'expo-status-bar';
import { StyleSheet, View } from 'react-native';
import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';
import HomeScreen from './HomeScreen';

const AppNavigator = createStackNavigator(
  {
    HomeScreen: HomeScreen,
  },
  {
    initialRouteName: 'HomeScreen',  // 초기 화면을 HomeScreen으로 설정
  }
);

const AppContainer = createAppContainer(AppNavigator);  // createStackNavigator의 결과를 AppContainer로 감싸서 export

export default function App() {
  return (
    <View style={styles.container}>
      <AppContainer />
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
  },
});
