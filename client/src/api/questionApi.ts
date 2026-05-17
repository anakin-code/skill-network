import type { Answer, Question } from "../types/question";

const API_BASE = "http://localhost:8080";

export async function fetchQuestions(): Promise<Question[]> {
  const response = await fetch(`${API_BASE}/api/questions`);

  if (!response.ok) {
    throw new Error("質問一覧の取得に失敗しました。");
  }

  return await response.json();
}

export async function fetchQuestion(questionId: string): Promise<Question> {
  const response = await fetch(`${API_BASE}/api/questions/${questionId}`);

  if (!response.ok) {
    throw new Error("質問の取得に失敗しました。");
  }

  return await response.json();
}

export async function createQuestion(
  title: string,
  content: string,
  userKey: string
): Promise<void> {
  const response = await fetch(`${API_BASE}/api/questions`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ title, content, userKey }),
  });

  if (!response.ok) {
    throw new Error("質問投稿に失敗しました。");
  }
}

export async function fetchAnswers(questionId: string): Promise<Answer[]> {
  const response = await fetch(`${API_BASE}/api/questions/${questionId}/answers`);

  if (!response.ok) {
    throw new Error("回答一覧の取得に失敗しました。");
  }

  return await response.json();
}

export async function createAnswer(
  questionId: string,
  content: string,
  userKey: string
): Promise<void> {
  const response = await fetch(`${API_BASE}/api/questions/${questionId}/answers`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ content, userKey }),
  });

  if (!response.ok) {
    throw new Error("回答投稿に失敗しました。");
  }
}

export async function selectBestAnswer(
  questionId: string,
  answerId: number,
  userKey: string
): Promise<void> {
  const response = await fetch(
    `${API_BASE}/api/questions/${questionId}/best-answer/${answerId}`,
    {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ userKey }),
    }
  );

  if (!response.ok) {
    throw new Error("ベストアンサーの選択に失敗しました。");
  }
}

export async function searchQuestions(keyword: string): Promise<Question[]> {
  const response = await fetch(
    `${API_BASE}/api/questions/search?keyword=${encodeURIComponent(keyword)}`
  );

  if (!response.ok) {
    throw new Error("検索に失敗しました。");
  }

  return await response.json();
}