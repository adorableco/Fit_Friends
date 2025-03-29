/** @format */

import React, { useEffect, useState } from "react";
import axios from "axios";
import { Text, StyleSheet, View, TouchableOpacity } from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { EXPO_PUBLIC_API_URL } from "@env";

export default function MatchListScreen({ navigation, route }) {
  const [matchList, setMatchList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [category, setCategory] = useState("badminton");
  const API_URL = EXPO_PUBLIC_API_URL;

  const categories = [
    { id: "badminton", name: "배드민턴" },
    { id: "soccer", name: "축구" },
    { id: "basketball", name: "농구" },
  ];

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

    // 새로 등록된 게시물이 있는 경우 해당 게시물로 스크롤
    if (route.params?.newPostId) {
      // 새로고침 후 새로 등록된 게시물을 찾아서 표시
      const newPost = matchList.find(
        (post) => post.postId === route.params.newPostId,
      );
      if (newPost) {
        // TODO: 해당 게시물로 스크롤하는 로직 추가
      }
    }
  }, [category, route.params?.refresh]);

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
        if (res.data.code === 200) {
          alert("참가 신청이 완료되었습니다!");
        }
      })
      .catch((error) => {
        if (error.response?.data?.code === 400) {
          alert("참가 조건이 충족되지 않습니다.");
        } else {
          alert("참가 신청 중 오류가 발생했습니다.");
        }
      });
  };

  const formatDateTime = (dateTimeStr) => {
    if (!dateTimeStr) return "";

    const date = new Date(dateTimeStr);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hours = date.getHours();
    const minutes = date.getMinutes();

    return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;
  };

  return (
    <View style={styles.container}>
      <View style={styles.categoryContainer}>
        {categories.map((cat) => (
          <TouchableOpacity
            key={cat.id}
            style={[
              styles.categoryButton,
              category === cat.id && styles.categoryButtonActive,
            ]}
            onPress={() => {
              setCategory(cat.id);
              fetchMatchList(cat.id);
            }}
          >
            <Text
              style={[
                styles.categoryText,
                category === cat.id && styles.categoryTextActive,
              ]}
            >
              {cat.name}
            </Text>
          </TouchableOpacity>
        ))}
      </View>
      {isLoading ? (
        <Text style={styles.loadingText}>로딩중...</Text>
      ) : matchList.length === 0 ? (
        <View style={styles.emptyContainer}>
          <Text style={styles.emptyText}>게시물 없음</Text>
          <TouchableOpacity
            style={styles.createPostButton}
            onPress={() => {
              navigation.navigate("PostingScreen", { category: category });
            }}
          >
            <Text style={styles.createPostButtonText}>게시물 등록하기</Text>
          </TouchableOpacity>
        </View>
      ) : (
        matchList.map((match) => (
          <View style={styles.matchBox} key={match.postId}>
            <img src={match.userImage} style={styles.img} />
            <View style={styles.detailBox}>
              <Text style={styles.title}>{match.title}</Text>
              <View style={styles.infoRow}>
                <Text style={styles.detailBold}>현재 인원 수 :</Text>
                <Text style={styles.detail}>
                  {match.match.currentHeadCnt} 명 / {match.match.headCnt} 명
                </Text>
              </View>
              <View style={styles.infoRow}>
                <Text style={styles.detailBold}>장소 :</Text>
                <Text style={styles.detail}>{match.match.place}</Text>
              </View>
              <View style={styles.infoRow}>
                <Text style={styles.detailBold}>시작 시간 :</Text>
                <Text style={styles.detail}>
                  {formatDateTime(match.match.startTime)}
                </Text>
              </View>
              <View style={styles.infoRow}>
                <Text style={styles.detailBold}>종료 시간 :</Text>
                <Text style={styles.detail}>
                  {formatDateTime(match.match.endTime)}
                </Text>
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
              {match.match.currentHeadCnt == match.match.headCnt ? (
                <Text style={styles.ended}>모집 마감</Text>
              ) : (
                <TouchableOpacity
                  style={styles.btn}
                  onPress={() => {
                    onClickApplyBtn(match.match.matchId);
                  }}
                >
                  <Text style={styles.btnText}>참가 신청</Text>
                </TouchableOpacity>
              )}
            </View>
          </View>
        ))
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f5f5f5",
  },
  categoryContainer: {
    padding: 8,
    backgroundColor: "#fff",
    borderBottomWidth: 1,
    borderBottomColor: "#e0e0e0",
    flexDirection: "row",
    justifyContent: "space-around",
    elevation: 2,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 1,
    },
    shadowOpacity: 0.1,
    shadowRadius: 2,
  },
  categoryButton: {
    paddingHorizontal: 12,
    paddingVertical: 6,
    borderRadius: 12,
    backgroundColor: "#f0f0f0",
    width: 70,
    height: 32,
    justifyContent: "center",
    alignItems: "center",
  },
  categoryButtonActive: {
    backgroundColor: "#4CAF50",
  },
  categoryText: {
    fontSize: 13,
    color: "#666",
    fontWeight: "500",
  },
  categoryTextActive: {
    color: "#fff",
  },
  loadingText: {
    textAlign: "center",
    marginTop: 20,
    fontSize: 16,
    color: "#666",
  },
  matchBox: {
    backgroundColor: "#fff",
    margin: 12,
    padding: 16,
    borderRadius: 16,
    flexDirection: "row",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  img: {
    width: 60,
    height: 60,
    borderRadius: 30,
    marginRight: 16,
    borderWidth: 2,
    borderColor: "#4CAF50",
  },
  detailBox: {
    flex: 1,
    alignItems: "center",
  },
  title: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 12,
    color: "#333",
    width: "100%",
  },
  infoRow: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 6,
    width: "100%",
  },
  detail: {
    fontSize: 14,
    color: "#666",
    marginLeft: 8,
  },
  detailBold: {
    fontSize: 14,
    color: "#333",
    fontWeight: "600",
  },
  ended: {
    marginTop: 12,
    paddingVertical: 6,
    paddingHorizontal: 12,
    backgroundColor: "#ff4444",
    color: "white",
    borderRadius: 16,
    fontSize: 13,
    fontWeight: "600",
    textAlign: "center",
  },
  btn: {
    marginTop: 12,
    paddingVertical: 6,
    paddingHorizontal: 16,
    backgroundColor: "#4CAF50",
    borderRadius: 16,
  },
  btnText: {
    color: "white",
    fontSize: 14,
    fontWeight: "600",
  },
  emptyContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
  },
  emptyText: {
    fontSize: 16,
    color: "#666",
    textAlign: "center",
    marginBottom: 20,
  },
  createPostButton: {
    backgroundColor: "#4CAF50",
    paddingHorizontal: 24,
    paddingVertical: 14,
    borderRadius: 25,
    elevation: 2,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
  },
  createPostButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "600",
  },
});
