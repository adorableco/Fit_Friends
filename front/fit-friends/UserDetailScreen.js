/** @format */

import axios from "axios";
import React, { useEffect, useState } from "react";
import { Text, StyleSheet, Image, View } from "react-native";
import MyMatchList from "./MyMatchList";
import AsyncStorage from "@react-native-async-storage/async-storage";

export default function UserDetailScreen({ route }) {
  const [userDetail, setUserDetail] = useState();
  const [isLoading, setIsLoading] = useState(true);
  const userId = route.params.userId;

  useEffect(() => {
    const fetchData = async () => {
      const token = await AsyncStorage.getItem("@accessToken");
      console.log(token);

      await axios
        .get(`http://fit-friends.duckdns.org:8081/api/user/${userId}`, {
          headers: {
            Authorization: token,
          },
        })
        .then((res) => {
          setIsLoading(false);
          console.log(res.data);
          setUserDetail(res.data);
        })
        .catch((e) => console.log(e));
    };
    fetchData();
  }, [isLoading]);

  if (isLoading) {
    return (
      <View>
        <Text>로딩중...</Text>
      </View>
    );
  }

  return (
    <View style={styles.view}>
      <img src={userDetail.picture} style={styles.img} />
      <Text style={styles.level}>{userDetail.level}</Text>
      <Text style={styles.name}>{userDetail.name}</Text>
      {userDetail.sMyDetail && (
        <Text style={{ marginTop: 10 }}>{userDetail.email}</Text>
      )}
      <Text style={styles.detail}>
        {userDetail.gender == "M" ? "남" : "여"} / {userDetail.age}
      </Text>
      <View
        style={{
          display: "flex",
          flexDirection: "row",
        }}
      >
        <button style={styles.btn}>개인 정보 수정</button>
        <button style={styles.btn}>로그아웃</button>
      </View>
      <Text style={styles.winningRate}>
        최근 10회 승률{" "}
        <Text style={styles.rate}>{userDetail.winningRate}%</Text>
      </Text>
      <Text style={styles.winningRate}>
        최근 10회 출석률{" "}
        <Text style={styles.rate}>{userDetail.attendanceRate}%</Text>
      </Text>
      {userDetail.isMyDetail &&
        userDetail.participationList.map((participation) => (
          <MyMatchList detail={participation} />
        ))}
    </View>
  );
}

const styles = StyleSheet.create({
  view: {
    justifyItem: "space-around",
    alignItems: "center",
  },
  level: {
    marginTop: 15,
    marginBottom: 10,
    backgroundColor: "#2196F3",
    borderRadius: 30,
    width: "max-content",
    padding: 10,
    color: "white",
  },
  img: {
    width: 110,
    height: 110,
    borderRadius: 90,
    marginTop: 50,
  },
  name: {
    fontWeight: 700,
    fontSize: 30,
    lineHeight: 31,
    textAlign: "center",
    color: "#000000",
  },
  detail: {
    fontWeight: 300,
    fontSize: 16,
    lineHeight: 18,
    textAlign: "center",
    color: "#000000",
    marginBottom: 10,
    marginTop: 10,
  },
  winningRate: {
    marginTop: 20,
    fontWeight: 700,
    fontSize: 16,
    lineHeight: 21,
    textAlign: "center",
    color: "#000000",
  },
  rate: {
    color: "#4CAF50",
    fontWeight: 700,
  },
  btn: {
    fontSize: 12,
    fontWeight: 600,
    marginTop: 5,
    marginRight: 10,
    marginBottom: 10,
    width: 90,
    height: 30,
    backgroundColor: "#4CAF50",
    borderRadius: 50,
    border: "none",
    color: "white",
  },
});
