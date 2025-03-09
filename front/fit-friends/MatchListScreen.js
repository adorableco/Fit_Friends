/** @format */

import React, { useEffect, useState } from "react";
import axios from "axios";
import { Text, StyleSheet, View, Button } from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import formatDateTime from "./FormatDateTime";
import { EXPO_PUBLIC_API_URL } from "@env";

export default function MatchListScreen() {
  const [matchList, setMatchList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [category, setCategory] = useState("badminton");
  const API_URL = EXPO_PUBLIC_API_URL;

  const fetchMatchList = async (category) => {
    setIsLoading(true);
    const token = await AsyncStorage.getItem("@accessToken");

    await axios
      .get(
        `${API_URL}/post-service/posts?category=${category}&levelType=beginner&genderType=F&ageType=teens`,

        {
          headers: {
            Authorization: token,
          },
        },
      )
      .then((res) => {
        setIsLoading(false);
        setMatchList(res.data.data);
      })
      .catch((e) => console.log(e));
  };

  useEffect(() => {
    fetchMatchList(category);
  }, [category]);

  const onClickApplyBtn = async (matchId) => {
    const token = await AsyncStorage.getItem("@accessToken");
    await axios
      .post(
        `${API_URL}/match-service/participations/${matchId}`,
        {},
        {
          headers: {
            Authorization: token,
          },
        },
      )
      .then((res) => {
        console.log(res.data);
        alert(res.data);
      });
  };

  return (
    <View style={styles.view}>
      <View style={styles.buttonContainer}>
        <Button
          title='Badminton'
          onPress={() => {
            setCategory("badminton");
            fetchMatchList("badminton");
          }}
          color={category === "badminton" ? "#2196F3" : "#000"}
        />
        <Button
          title='Soccer'
          onPress={() => {
            setCategory("soccer");
            fetchMatchList("soccer");
          }}
          color={category === "soccer" ? "#2196F3" : "#000"}
        />
        <Button
          title='Basketball'
          onPress={() => {
            setCategory("basketball");
            fetchMatchList("basketball");
          }}
          color={category === "basketball" ? "#2196F3" : "#000"}
        />
      </View>
      {isLoading ? (
        <Text>로딩중...</Text>
      ) : (
        matchList.map((match) => (
          <View style={styles.matchBox} key={match.postId}>
            <img src={match.userImage} style={styles.img} />
            <View style={styles.detailBox}>
              <Text style={styles.title}>{match.title}</Text>
              <Text style={styles.detail}>
                현재 인원 수 : {match.match.currentHeadCnt} 명 /{" "}
                {match.match.headCnt} 명
              </Text>
              <Text style={styles.detail}>장소 : {match.match.place}</Text>
              <Text style={styles.detail}>
                시작 시간 : {formatDateTime(match.match.startTime)}
              </Text>
              <Text style={styles.detail}>
                종료 시간 : {formatDateTime(match.match.endTime)}
              </Text>
              <Text style={styles.detail}>리더 : {match.userName}</Text>
              <Text style={styles.detail}>
                생성일 : {formatDateTime(match.createdDate)}
              </Text>
              <Text style={styles.detail}>
                수정일 : {formatDateTime(match.modifiedDate)}
              </Text>
              {match.match.currentHeadCnt == match.match.headCnt ? (
                <Text style={styles.ended}>모집 마감</Text>
              ) : (
                <button
                  style={styles.btn}
                  onClick={() => {
                    onClickApplyBtn(match.match.matchId);
                  }}
                >
                  참가 신청
                </button>
              )}
            </View>
          </View>
        ))
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  view: {
    alignItems: "center",
  },
  buttonContainer: {
    flexDirection: "row",
    justifyContent: "space-around",
    marginVertical: 10,
  },
  matchBox: {
    marginTop: 10,
    borderRadius: 20,
    borderColor: "#2196F3",
    backgroundColor: "white",
    flexDirection: "row",
    padding: 20,
  },
  img: {
    width: 90,
    height: 90,
    borderRadius: 90,
    marginTop: 50,
  },
  title: {
    fontWeight: 800,
    fontSize: 20,
    lineHeight: 23,
    textAlign: "left",
    color: "#000000",
    marginBottom: 10,
    marginLeft: 20,
  },
  detail: {
    marginLeft: 20,
    fontWeight: 400,
    fontSize: 13,
    lineHeight: 20,
    textAlign: "left",
    color: "#000000",
  },
  ended: {
    marginTop: 10,
    marginLeft: 20,
    marginBottom: 10,
    fontWeight: 700,
    fontSize: 16,
    lineHeight: 21,
    textAlign: "center",
    backgroundColor: "red",
    color: "white",
    padding: 8,
    width: "fit-content",
    borderRadius: 14,
  },
  btn: {
    fontWeight: 600,
    marginTop: 10,
    marginLeft: 20,
    marginBottom: 10,
    width: 80,
    height: 30,
    backgroundColor: "#4CAF50",
    borderRadius: 50,
    border: "none",
    color: "white",
  },
});
