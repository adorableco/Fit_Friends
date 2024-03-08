/** @format */

import React from "react";
import { Text, StyleSheet, Image, View } from "react-native";
import { AutoFocus, Camera, CameraType } from "expo-camera";

import AsyncStorage from "@react-native-async-storage/async-storage";

const MyMatchList = ({ detail }) => {
  const openCameraHandler = async () => {
    // 카메라에 대한 접근 권한을 얻을 수 있는지 묻는 함수입니다.
    const { status } = await Camera.requestCameraPermissionsAsync();

    // 권한을 획득하면 status가 granted 상태가 됩니다.
    if (status === "granted") {
      // Camera 컴포넌트가 있는 CameraScreen으로 이동합니다.
      navigation.navigate("CameraScreen");
    } else {
      alert("카메라 접근 허용은 필수입니다.");
    }
  };
  return (
    <View style={styles.listBox}>
      <Text style={styles.detail}>
        {/* <Image
          source={require(`./assets/${detail.match.category}.png`)}
          style={{ width: 30, height: 30, marginRight: 30, marginTop: 10 }}
        /> */}
        {detail.match.startTime}
      </Text>
      <Text style={{ marginBottom: "5px" }}>{detail.match.place}</Text>
      <Text>{detail.match.status}</Text>
      {detail.status == "end" ? (
        detail.win ? (
          <Text style={styles.winBox}>승리</Text>
        ) : (
          <Text style={styles.loseBox}>패배</Text>
        )
      ) : detail.status == "waited" ? (
        <button style={styles.btn}>참가 취소</button>
      ) : (
        <button style={styles.btn} onClick={openCameraHandler}>
          출석 체크
        </button>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  listBox: {
    marginTop: 15,
    display: "flex",
    alignItems: "center",
    width: 250,
    height: 100,
    borderRadius: 22,
    border: "solid #4CAF50 1.5px",
  },
  detail: {
    display: "flex",
    alignItems: "center",
    fontWeight: 700,
    fontSize: 15,
    lineHeight: 20,
    textAlign: "center",
    color: "#000000",
  },
  winBox: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#2C79F1",
    width: 33,
    height: 33,
    borderRadius: 33,
    color: "white",
    fontSize: 12,
    marginBottom: 10,
  },
  loseBox: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: "#F23562",
    width: 33,
    height: 33,
    borderRadius: 33,
    color: "white",
    fontSize: 12,
    marginBottom: 10,
  },
  btn: {
    fontSize: 12,
    fontWeight: 600,
    marginTop: 5,
    width: 75,
    height: 23,
    backgroundColor: "#4CAF50",
    borderRadius: 50,
    border: "none",
    color: "white",
  },
});

export default MyMatchList;
