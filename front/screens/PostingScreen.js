/** @format */

import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  TouchableOpacity,
  ScrollView,
  Alert,
  Platform,
} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import { EXPO_PUBLIC_API_URL } from "@env";
import { Picker } from "@react-native-picker/picker";
import Toast from "react-native-toast-message";

export default function PostingScreen({ navigation, route }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [headCnt, setHeadCnt] = useState("2");
  const [place, setPlace] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [level, setLevel] = useState("");
  const [age, setAge] = useState("");
  const [gender, setGender] = useState("");
  const [category, setCategory] = useState("");
  const API_URL = EXPO_PUBLIC_API_URL;

  useEffect(() => {
    // route.params에서 카테고리 정보를 받아와서 설정
    if (route.params?.category) {
      setCategory(route.params.category);
    }
  }, [route.params]);

  const headCountOptions = [
    { label: "2명", value: "2" },
    { label: "3명", value: "3" },
    { label: "4명", value: "4" },
    { label: "5명", value: "5" },
    { label: "6명", value: "6" },
    { label: "7명", value: "7" },
    { label: "8명", value: "8" },
    { label: "9명", value: "9" },
    { label: "10명", value: "10" },
  ];

  const levelOptions = [
    { label: "초보", value: "beginner" },
    { label: "숙련", value: "master" },
  ];

  const ageOptions = [
    { label: "청소년", value: "youth" },
    { label: "10대", value: "teenager" },
    { label: "성인", value: "adult" },
  ];

  const genderOptions = [
    { label: "남성", value: "M" },
    { label: "여성", value: "F" },
  ];

  const categoryOptions = [
    { label: "배드민턴", value: "badminton" },
    { label: "농구", value: "basketball" },
    { label: "축구", value: "soccer" },
    { label: "달리기", value: "running" },
  ];

  const formatDateTime = (dateTimeStr) => {
    // YYYY-MM-DD HH:mm 형식을 YYYY-MM-DDTHH:mm 형식으로 변환
    return dateTimeStr.replace(" ", "T");
  };

  const handleSubmit = async () => {
    try {
      const token = await AsyncStorage.getItem("@accessToken");
      const userId = await AsyncStorage.getItem("@userId");

      if (
        !title ||
        !content ||
        !headCnt ||
        !place ||
        !startTime ||
        !endTime ||
        !category
      ) {
        Toast.show({
          type: "error",
          text1: "오류",
          text2: "필수 항목을 모두 입력해주세요.",
          position: "bottom",
        });
        return;
      }

      const postData = {
        title: title,
        content: content,
        category: category,
        match: {
          headCnt: parseInt(headCnt),
          place: place,
          startTime: formatDateTime(startTime),
          endTime: formatDateTime(endTime),
        },
        tag: {
          levelType: level || undefined,
          ageType: age || undefined,
          genderType: gender || undefined,
        },
      };

      const response = await axios.post(
        `${API_URL}/post-service/posts`,
        postData,
        {
          headers: {
            Authorization: token,
          },
        },
      );

      console.log("API Response:", response.data);

      if (response.data.result === "SUCCESS") {
        Toast.show({
          type: "success",
          text1: "성공",
          text2: "게시물이 등록되었습니다.",
          position: "bottom",
        });

        // 1.5초 후에 목록 화면으로 이동
        setTimeout(() => {
          navigation.navigate("MatchListScreen", {
            refresh: true,
            newPostId: response.data.data.postId,
          });
        }, 1500);
      } else {
        Toast.show({
          type: "error",
          text1: "오류",
          text2: "게시물 등록에 실패했습니다.",
          position: "bottom",
        });
      }
    } catch (error) {
      console.error("게시물 등록 실패:", error);
      Toast.show({
        type: "error",
        text1: "오류",
        text2: "게시물 등록에 실패했습니다.",
        position: "bottom",
      });
    }
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.formContainer}>
        <Text style={styles.label}>운동 종류</Text>
        <View style={styles.pickerContainer}>
          <Picker
            selectedValue={category}
            onValueChange={(itemValue) => setCategory(itemValue)}
            style={styles.picker}
            itemStyle={styles.pickerItem}
          >
            {categoryOptions.map((option) => (
              <Picker.Item
                key={option.value}
                label={option.label}
                value={option.value}
              />
            ))}
          </Picker>
        </View>

        <Text style={styles.label}>제목</Text>
        <TextInput
          style={styles.input}
          value={title}
          onChangeText={setTitle}
          placeholder='예시 : 배드민턴 모임 모집합니다.'
        />

        <Text style={styles.label}>내용</Text>
        <TextInput
          style={[styles.input, styles.textArea]}
          value={content}
          onChangeText={setContent}
          placeholder='예시 : 초보입니다~ 가볍게 배드민턴 치실 분 구해요!'
          multiline
          numberOfLines={4}
        />

        <Text style={styles.label}>모집 인원 (작성자 제외)</Text>
        <View style={styles.pickerContainer}>
          <Picker
            selectedValue={headCnt}
            onValueChange={(itemValue) => setHeadCnt(itemValue)}
            style={styles.picker}
            itemStyle={styles.pickerItem}
          >
            {headCountOptions.map((option) => (
              <Picker.Item
                key={option.value}
                label={option.label}
                value={option.value}
              />
            ))}
          </Picker>
        </View>

        <Text style={styles.label}>장소</Text>
        <TextInput
          style={styles.input}
          value={place}
          onChangeText={setPlace}
          placeholder='장소를 입력하세요'
        />

        <Text style={styles.label}>시작 시간</Text>
        <TextInput
          style={styles.input}
          value={startTime}
          onChangeText={setStartTime}
          placeholder='YYYY-MM-DD HH:mm 형식으로 입력하세요'
        />

        <Text style={styles.label}>종료 시간</Text>
        <TextInput
          style={styles.input}
          value={endTime}
          onChangeText={setEndTime}
          placeholder='YYYY-MM-DD HH:mm 형식으로 입력하세요'
        />

        <Text style={styles.label}>레벨</Text>
        <View style={styles.pickerContainer}>
          <Picker
            selectedValue={level}
            onValueChange={(itemValue) => setLevel(itemValue)}
            style={styles.picker}
            itemStyle={styles.pickerItem}
          >
            {levelOptions.map((option) => (
              <Picker.Item
                key={option.value}
                label={option.label}
                value={option.value}
              />
            ))}
          </Picker>
        </View>

        <Text style={styles.label}>나이</Text>
        <View style={styles.pickerContainer}>
          <Picker
            selectedValue={age}
            onValueChange={(itemValue) => setAge(itemValue)}
            style={styles.picker}
            itemStyle={styles.pickerItem}
          >
            {ageOptions.map((option) => (
              <Picker.Item
                key={option.value}
                label={option.label}
                value={option.value}
              />
            ))}
          </Picker>
        </View>

        <Text style={styles.label}>성별</Text>
        <View style={styles.pickerContainer}>
          <Picker
            selectedValue={gender}
            onValueChange={(itemValue) => setGender(itemValue)}
            style={styles.picker}
            itemStyle={styles.pickerItem}
          >
            {genderOptions.map((option) => (
              <Picker.Item
                key={option.value}
                label={option.label}
                value={option.value}
              />
            ))}
          </Picker>
        </View>

        <TouchableOpacity style={styles.submitButton} onPress={handleSubmit}>
          <Text style={styles.submitButtonText}>게시물 등록</Text>
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f5f5f5",
  },
  formContainer: {
    padding: 20,
  },
  label: {
    fontSize: 16,
    fontWeight: "600",
    color: "#333",
    marginBottom: 8,
  },
  input: {
    backgroundColor: "#fff",
    padding: 12,
    borderRadius: 8,
    marginBottom: 16,
    fontSize: 16,
    borderWidth: 1,
    borderColor: "#ddd",
  },
  textArea: {
    height: 120,
    textAlignVertical: "top",
  },
  pickerContainer: {
    backgroundColor: "#fff",
    borderRadius: 8,
    marginBottom: 16,
    borderWidth: 1,
    borderColor: "#ddd",
    overflow: "hidden",
  },
  picker: {
    height: Platform.OS === "ios" ? 150 : 50,
    width: "100%",
  },
  pickerItem: {
    fontSize: 16,
  },
  submitButton: {
    backgroundColor: "#4CAF50",
    padding: 15,
    borderRadius: 8,
    alignItems: "center",
    marginTop: 20,
  },
  submitButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "600",
  },
});
