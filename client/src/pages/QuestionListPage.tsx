import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import { fetchQuestions } from "../api/questionApi";
import type { Question } from "../types/question";
import { cardStyle } from "../styles/commonStyles";

function QuestionListPage() {
  const [questions, setQuestions] = useState<Question[]>([]);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    fetchQuestions()
      .then((data) => setQuestions(data))
      .catch((error: Error) => setErrorMessage(error.message));
  }, []);

  return (
    <div>
      <h2>質問一覧</h2>

      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}

      {questions.map((question) => (
        <Link
          key={question.id}
          to={`/questions/${question.id}`}
          style={{ textDecoration: "none", color: "inherit" }}
        >
          <div style={cardStyle}>
            <h3>{question.title}</h3>
            <p>{question.content}</p>

            {question.resolved ? (
              <strong style={{ color: "green" }}>解決済み</strong>
            ) : (
              <strong style={{ color: "red" }}>未解決</strong>
            )}
          </div>
        </Link>
      ))}
    </div>
  );
}

export default QuestionListPage;