/** @format */

import React, { useState, useEffect } from "react";
import { Text, View, StyleSheet, Button } from "react-native";
import { BarCodeScanner } from "expo-barcode-scanner";
import axios from "axios";

export default function CameraScreen() {
  const [hasPermission, setHasPermission] = useState(null);
  const [scanned, setScanned] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const getBarCodeScannerPermissions = async () => {
      const { status } = await BarCodeScanner.requestPermissionsAsync();
      setHasPermission(status === "granted");
    };

    getBarCodeScannerPermissions();
  }, []);

  const checkQR = async (url) => {
    //const token = await AsyncStorage.getItem("@accessToken");
    setIsLoading(true);
    const token =
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cGR1c2RsMTAwMUBrbnUuYWMua3IiLCJyb2xlcyI6IlVTRVIiLCJpYXQiOjE3MDYzNDA5MDksImV4cCI6MTcwNjM0MTIwOX0.t9ARl5YRtIHTlETF8W4yvBHxh5_f351H-Z2AYUp0-b0";
    try {
      await axios
        .post(url, {
          headers: {
            "Access-Control-Allow-Origin": "http://localhost:19006",
            Authorization: token,
            withCredentials: true,
          },
        })
        .then((res) => {
          setIsLoading(false);
          alert(res.data);
          navigation.navigate("MatchListScreen");
        });
    } catch (e) {
      console.error(e);
    }
  };

  const handleBarCodeScanned = ({ type, data }) => {
    setScanned(true);
    alert(`Bar code with type ${type} and data ${data} has been scanned!`);
    console.log(data);
    checkQR(data);
  };

  if (hasPermission === null) {
    return <Text>Requesting for camera permission</Text>;
  }
  if (hasPermission === false) {
    return <Text>No access to camera</Text>;
  }

  return (
    <View style={styles.container}>
      <BarCodeScanner
        onBarCodeScanned={scanned ? undefined : handleBarCodeScanned}
        style={StyleSheet.absoluteFillObject}
      />
      {scanned && (
        <Button title={"Tap to Scan Again"} onPress={() => setScanned(false)} />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "column",
    justifyContent: "center",
  },
});
