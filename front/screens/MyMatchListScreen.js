/** @format */

import React, { useState, useEffect } from "react";
import { View, Text, TouchableOpacity } from "react-native";
import styles from "../styles/styles";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import { EXPO_PUBLIC_API_URL } from "@env";
import formatDateTime from "../FormatDateTime";

const MyMatchListScreen = () => {
  const [matches, setMatches] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const API_URL = EXPO_PUBLIC_API_URL;

  const fetchMyMatchList = async () => {
    setIsLoading(true);
    const token = await AsyncStorage.getItem("@accessToken");
    const userId = await AsyncStorage.getItem("@userId");
    await axios
      .get(`${API_URL}/match-service/participations/users/${userId}`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        console.log(res.data.data.participationList);
        setIsLoading(false);
        setMatches(res.data.data.participationList);
      })
      .catch((e) => {
        console.log(e);
        setIsLoading(false);
      });
  };

  useEffect(() => {
    fetchMyMatchList();
  }, []);

  const onClickApplyBtn = (matchId) => {
    // Implement the logic to apply for the match
  };

  if (isLoading) {
    return (
      <View style={styles.container}>
        <Text style={styles.loadingText}>로딩중...</Text>
      </View>
    );
  }

  if (matches.length === 0) {
    return (
      <View style={styles.container}>
        <Text style={styles.emptyText}>참가 신청한 모임이 없습니다.</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      {matches.map((match) => (
        <View style={styles.matchBox} key={match.postId}>
          <img src={match.userImage} style={styles.img} />
          <View style={styles.detailBox}>
            <Text style={styles.title}>{match.title}</Text>
            <View style={styles.infoRow}>
              <Text style={styles.detailBold}>현재 인원 수 :</Text>
              <Text style={styles.detail}>
                {match.currentHeadCnt} 명 / {match.headCnt} 명
              </Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.detailBold}>장소 :</Text>
              <Text style={styles.detail}>{match.place}</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.detailBold}>시작 시간 :</Text>
              <Text style={styles.detail}>
                {formatDateTime(match.startTime)}
              </Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.detailBold}>종료 시간 :</Text>
              <Text style={styles.detail}>{formatDateTime(match.endTime)}</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.detailBold}>리더 :</Text>
              <Text style={styles.detail}>{match.userName}</Text>
            </View>
            <View style={styles.infoRow}>
              <Text style={styles.detailBold}>
                {match.createdDate === match.modifiedDate
                  ? "생성일 :"
                  : "수정일 :"}
              </Text>
              <Text style={styles.detail}>
                {formatDateTime(
                  match.createdDate === match.modifiedDate
                    ? match.createdDate
                    : match.modifiedDate,
                )}
              </Text>
            </View>
            {match.currentHeadCnt == match.headCnt ? (
              <Text style={styles.ended}>모집 마감</Text>
            ) : (
              <TouchableOpacity
                style={styles.btn}
                onPress={() => {
                  onClickApplyBtn(match.matchId);
                }}
              >
                <Text style={styles.btnText}>참가 신청</Text>
              </TouchableOpacity>
            )}
          </View>
        </View>
      ))}
    </View>
  );
};

export default MyMatchListScreen;
