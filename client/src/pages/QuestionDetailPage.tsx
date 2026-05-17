import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import {
  createAnswer,
  fetchAnswers,
  fetchQuestion,
  selectBestAnswer,
} from "../api/questionApi";
import type { Answer, Question } from "../types/question";
import {
  buttonStyle,
  cardStyle,
  formStyle,
  inputStyle,
} from "../styles/commonStyles";

function QuestionDetailPage() {
  const { questionId } = useParams();

  const [question, setQuestion] = useState<Question | null>(null);
  const [answers, setAnswers] = useState<Answer[]>([]);
  const [answerContent, setAnswerContent] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const loadQuestion = async () => {
    if (!questionId) return;

    const data = await fetchQuestion(questionId);
    setQuestion(data);
  };

  const loadAnswers = async () => {
    if (!questionId) return;

    const data = await fetchAnswers(questionId);
    setAnswers(data);
  };

  useEffect(() => {
    setErrorMessage("");

    Promise.all([loadQuestion(), loadAnswers()]).catch((error: Error) => {
      setErrorMessage(error.message);
    });
  }, [questionId]);

  const answerSubmitHandler = async (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    setErrorMessage("");

    if (!questionId) return;

    const userKey = localStorage.getItem("userKey");

    if (!userKey) {
      setErrorMessage("ログインしてください。");
      return;
    }

    if (answerContent.trim() === "") {
      setErrorMessage("回答を入力してください。");
      return;
    }

    try {
      await createAnswer(questionId, answerContent, userKey);
      setAnswerContent("");
      await loadAnswers();
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      }
    }
  };

  const bestAnswerClickHandler = async (answerId: number) => {
    setErrorMessage("");

    if (!questionId) return;

    const userKey = localStorage.getItem("userKey");

    if (!userKey) {
      setErrorMessage("ログインしてください。");
      return;
    }

    try {
      await selectBestAnswer(questionId, answerId, userKey);
      await loadQuestion();
      await loadAnswers();
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      }
    }
  };

  if (!question) {
    return <p>読み込み中...</p>;
  }

  const loginUserId = Number(localStorage.getItem("userId"));
  const isQuestionOwner = question.userId === loginUserId;

  return (
    <div>
      <h2>質問詳細</h2>

      <div style={cardStyle}>
        <h3>{question.title}</h3>
        <p>{question.content}</p>
        <p>投稿者：{question.displayName}</p>

        {question.resolved ? (
          <strong style={{ color: "green" }}>解決済み</strong>
        ) : (
          <strong style={{ color: "red" }}>未解決</strong>
        )}
      </div>

      <h3>回答一覧</h3>

      {answers.length === 0 && <p>まだ回答はありません。</p>}

      {answers.map((answer) => (
        <div key={answer.id} style={cardStyle}>
          {question.bestAnswerId === answer.id && (
            <p style={{ color: "orange", fontWeight: "bold" }}>
              ⭐ ベストアンサー
            </p>
          )}

          <p>{answer.content}</p>
          <p>回答者：{answer.displayName}</p>

          {!question.resolved && isQuestionOwner && (
            <button
              type="button"
              onClick={() => bestAnswerClickHandler(answer.id)}
              style={buttonStyle}
            >
              ベストアンサーにする
            </button>
          )}
        </div>
      ))}

      <form onSubmit={answerSubmitHandler} style={formStyle}>
        <textarea
          value={answerContent}
          onChange={(event) => setAnswerContent(event.target.value)}
          placeholder="回答を書く"
          rows={5}
          style={inputStyle}
        />

        {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}

        <button type="submit" style={buttonStyle}>
          回答投稿
        </button>
      </form>
    </div>
  );
}

export default QuestionDetailPage;