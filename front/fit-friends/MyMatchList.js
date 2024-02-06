/** @format */

import axios from "axios";
import React, { useState } from "react";
import { Text, StyleSheet, Image, View } from "react-native";

const MyMatchList = ({ detail }) => {
  return (
    <View style={styles.listBox}>
      <Text style={styles.detail}>
        <Image
          source={require(`./assets/${detail.match.category}.png`)}
          style={{ width: 30, height: 30, marginRight: 30, marginTop: 10 }}
        />
        {detail.match.startTime}
      </Text>
      <Text>{detail.match.place}</Text>
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
        <button style={styles.btn}>출석 체크</button>
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
