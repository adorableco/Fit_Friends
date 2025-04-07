/** @format */

import axios from "axios";
import React, { useEffect, useState } from "react";
import { Text, StyleSheet, View, TouchableOpacity } from "react-native";
import MyMatchList from "./MyMatchList";
import AsyncStorage from "@react-native-async-storage/async-storage";
import Modal from "react-modal";
import { EXPO_PUBLIC_API_URL } from "@env";

export default function UserDetailScreen({ route, navigation }) {
  const [userDetail, setUserDetail] = useState();
  const [isLoading, setIsLoading] = useState(true);
  const [isModifying, setIsModifying] = useState(false);
  const [genderVisible, setGenderVisible] = useState(true);
  const [ageVisible, setAgeVisible] = useState(true);
  const [changeName, setChangeName] = useState("");
  const API_URL = EXPO_PUBLIC_API_URL;

  const onChangeName = (e) => {
    setChangeName(e.target.value);
  };

  const onClickLogout = async () => {
    await AsyncStorage.removeItem("@accessToken");
    await AsyncStorage.removeItem("@userId");
    alert("로그아웃이 완료되었습니다.");
    navigation.navigate("HomeScreen");
  };

  const onClickModifyBtn = async () => {
    const token = await AsyncStorage.getItem("@accessToken");
    await axios
      .put(
        `${API_URL}/user-service/users`,
        {
          name: changeName,
          ageVisible: ageVisible,
          genderVisible: genderVisible,
        },
        {
          headers: {
            Authorization: token,
          },
        },
      )
      .then((res) => {
        setIsModifying(false);
      });
  };

  useEffect(() => {
    const fetchData = async () => {
      const token = await AsyncStorage.getItem("@accessToken");
      const userId = await AsyncStorage.getItem("@userId");

      await axios
        .get(`${API_URL}/user-service/users/${userId}`, {
          headers: {
            Authorization: token,
          },
        })
        .then((res) => {
          setIsLoading(false);
          setUserDetail(res.data.data);
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
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.headerTitle}>내 프로필</Text>
      </View>
      <View style={styles.profileSection}>
        <img src={userDetail.picture} style={styles.profileImage} />
        <View style={styles.profileInfo}>
          <Text style={styles.userName}>{userDetail.name}</Text>
          <Text style={styles.userEmail}>{userDetail.email}</Text>
        </View>
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>내 통계</Text>
        <View style={styles.statsContainer}>
          <View style={styles.statItem}>
            <Text style={styles.statValue}>{userDetail.level}</Text>
            <Text style={styles.statLabel}>레벨</Text>
          </View>
          <View style={styles.statItem}>
            <Text style={styles.statValue}>{userDetail.winningRate}%</Text>
            <Text style={styles.statLabel}>승률</Text>
          </View>
          <View style={styles.statItem}>
            <Text style={styles.statValue}>
              {userDetail.attendanceRate == "Infinity"
                ? "경기 진행 전무"
                : userDetail.attendanceRate}
              %
            </Text>
            <Text style={styles.statLabel}>출석률</Text>
          </View>
        </View>
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>내 정보</Text>
        <Text style={styles.userDetail}>
          {userDetail.gender == "M"
            ? "남"
            : userDetail.gender == "F"
            ? "여"
            : "성별 비공개"}{" "}
          /{" "}
          {userDetail.age == "youngs"
            ? "어린이"
            : userDetail.age == "teens"
            ? "청소년"
            : userDetail.age == "adults"
            ? "성인"
            : "나이대 비공개"}{" "}
        </Text>
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>참가 기록</Text>
        {userDetail.isMyDetail &&
          userDetail.participationList.map((participation) => (
            <MyMatchList detail={participation} />
          ))}
      </View>
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>계정 관리</Text>
        <View style={styles.actionButtons}>
          <Modal isOpen={isModifying} style={styles.modal}>
            <View style={styles.modalHeader}>
              <h2 style={{ color: "#4CAF50" }}>회원 정보 수정</h2>
              <TouchableOpacity
                style={styles.closeButton}
                onPress={() => setIsModifying(false)}
              >
                <Text style={styles.closeButtonText}>✕</Text>
              </TouchableOpacity>
            </View>
            <input
              style={styles.input}
              onChange={onChangeName}
              placeholder='변경할 닉네임'
            />

            <View style={styles.imageUploadContainer}>
              <Text style={styles.inputLabel}>프로필 이미지</Text>
              <TouchableOpacity style={styles.imageUploadButton}>
                <Text style={styles.imageUploadText}>📸 이미지 선택</Text>
                <input type='file' accept='image/*' style={styles.fileInput} />
              </TouchableOpacity>
            </View>

            <View style={styles.checkboxContainer}>
              <TouchableOpacity
                style={styles.checkboxRow}
                onPress={() => setGenderVisible(!genderVisible)}
              >
                <View
                  style={[
                    styles.checkbox,
                    genderVisible && styles.checkboxChecked,
                  ]}
                >
                  {genderVisible && <Text style={styles.checkmark}>✓</Text>}
                </View>
                <Text style={styles.checkboxLabel}>성별 공개</Text>
              </TouchableOpacity>

              <TouchableOpacity
                style={[styles.checkboxRow, { marginTop: 12 }]}
                onPress={() => setAgeVisible(!ageVisible)}
              >
                <View
                  style={[
                    styles.checkbox,
                    ageVisible && styles.checkboxChecked,
                  ]}
                >
                  {ageVisible && <Text style={styles.checkmark}>✓</Text>}
                </View>
                <Text style={styles.checkboxLabel}>나이대 공개</Text>
              </TouchableOpacity>
            </View>

            <button style={styles.btn} onClick={onClickModifyBtn}>
              정보 수정하기
            </button>
          </Modal>
          <button
            style={styles.btn}
            onClick={() => {
              setIsModifying(!isModifying);
            }}
          >
            회원 정보 수정
          </button>
          <button style={styles.btn} onClick={onClickLogout}>
            로그아웃
          </button>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f5f5f5",
  },
  header: {
    backgroundColor: "#fff",
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: "#e0e0e0",
    alignItems: "center",
    elevation: 2,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 1,
    },
    shadowOpacity: 0.1,
    shadowRadius: 2,
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: "600",
    color: "#333",
  },
  profileSection: {
    backgroundColor: "#fff",
    padding: 20,
    marginTop: 12,
    marginHorizontal: 12,
    borderRadius: 16,
    flexDirection: "row",
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  profileImage: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginRight: 16,
    borderWidth: 2,
    borderColor: "#4CAF50",
  },
  profileInfo: {
    flex: 1,
  },
  userName: {
    fontSize: 20,
    fontWeight: "bold",
    color: "#333",
    marginBottom: 4,
  },
  userEmail: {
    fontSize: 14,
    color: "#666",
    marginBottom: 8,
  },
  section: {
    backgroundColor: "#fff",
    padding: 16,
    marginTop: 12,
    marginHorizontal: 12,
    borderRadius: 16,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  sectionTitle: {
    fontSize: 16,
    fontWeight: "600",
    color: "#333",
    marginBottom: 12,
  },
  statsContainer: {
    flexDirection: "row",
    justifyContent: "space-around",
    paddingVertical: 12,
  },
  statItem: {
    alignItems: "center",
    flex: 1,
  },
  statValue: {
    fontSize: 18,
    fontWeight: "600",
    color: "#4CAF50",
    marginBottom: 4,
  },
  statLabel: {
    fontSize: 13,
    color: "#666",
  },
  matchItem: {
    flexDirection: "row",
    alignItems: "center",
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderBottomColor: "#f0f0f0",
  },
  matchItemLast: {
    borderBottomWidth: 0,
  },
  matchImage: {
    width: 50,
    height: 50,
    borderRadius: 25,
    marginRight: 12,
  },
  matchInfo: {
    flex: 1,
  },
  matchTitle: {
    fontSize: 15,
    fontWeight: "500",
    color: "#333",
    marginBottom: 4,
  },
  matchDetail: {
    fontSize: 13,
    color: "#666",
  },
  emptyText: {
    textAlign: "center",
    color: "#666",
    fontSize: 14,
    marginTop: 20,
  },
  loadingText: {
    textAlign: "center",
    color: "#666",
    fontSize: 14,
    marginTop: 20,
  },
  modal: {
    overlay: {
      backgroundColor: "rgba(0, 0, 0, 0.5)",
    },
    content: {
      position: "absolute",
      top: "50%",
      left: "50%",
      transform: "translate(-50%, -50%)",
      width: "90%",
      maxWidth: 400,
      height: "auto",
      minHeight: 500,
      display: "flex",
      flexDirection: "column",
      justifyContent: "flex-start",
      alignItems: "center",
      padding: 32,
      borderRadius: 20,
      backgroundColor: "white",
      boxShadow: "0 2px 10px rgba(0, 0, 0, 0.1)",
    },
  },
  modalContent: {
    width: "100%",
    display: "flex",
    flexDirection: "column",
    gap: 20,
  },
  input: {
    borderWidth: 1,
    borderColor: "#E0E0E0",
    borderRadius: 12,
    padding: 12,
    height: 48,
    fontSize: 16,
    backgroundColor: "#F8F8F8",
    marginBottom: 16,
    paddingHorizontal: 16,
    elevation: 0,
    shadowColor: "transparent",
    shadowOffset: {
      width: 0,
      height: 0,
    },
    shadowOpacity: 0,
    shadowRadius: 0,
  },
  imageUploadContainer: {
    marginBottom: 20,
  },
  imageUploadButton: {
    borderWidth: 2,
    borderStyle: "dashed",
    borderColor: "#4CAF50",
    borderRadius: 12,
    padding: 16,
    backgroundColor: "#F8F8F8",
    alignItems: "center",
    flexDirection: "row",
    justifyContent: "center",
  },
  imageUploadText: {
    color: "#4CAF50",
    fontSize: 16,
    fontWeight: "500",
  },
  fileInput: {
    position: "absolute",
    width: "100%",
    height: "100%",
    opacity: 0,
  },
  checkboxContainer: {
    marginBottom: 24,
    backgroundColor: "#F8F8F8",
    borderRadius: 12,
    padding: 16,
  },
  checkboxRow: {
    flexDirection: "row",
    alignItems: "center",
  },
  checkbox: {
    width: 24,
    height: 24,
    borderRadius: 6,
    borderWidth: 2,
    borderColor: "#4CAF50",
    marginRight: 12,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "white",
  },
  checkboxChecked: {
    backgroundColor: "#4CAF50",
  },
  checkmark: {
    color: "white",
    fontSize: 16,
    fontWeight: "bold",
  },
  checkboxLabel: {
    fontSize: 16,
    color: "#333",
    fontWeight: "500",
  },
  btn: {
    fontWeight: 600,
    marginTop: 5,
    marginRight: 10,
    marginBottom: 10,
    width: 100,
    height: 40,
    backgroundColor: "#4CAF50",
    borderRadius: 50,
    border: "none",
    color: "white",
  },
  actionButtons: {
    display: "flex",
    flexDirection: "row",
  },
  modalHeader: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    width: "100%",
    marginBottom: 20,
  },
  closeButton: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: "#F0F0F0",
    justifyContent: "center",
    alignItems: "center",
  },
  closeButtonText: {
    fontSize: 18,
    color: "#666",
    fontWeight: "bold",
  },
});
