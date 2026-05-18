import type { CareerResponse } from "../types/api";

type Props = {
  careers: CareerResponse[];
};

export default function CareerPanel({ careers }: Props) {
  return (
    <div className="card">
      <h3>経歴・スキル履歴</h3>

      {careers.length === 0 && <p className="muted">経歴がありません</p>}

      <div className="career-list">
        {careers.map((career) => (
          <div key={career.careerId} className="career-item">
            <div className="career-title">
              <strong>{career.projectName}</strong>
              <span>
                {career.startTime} 〜 {career.endTime ?? "現在"}
              </span>
            </div>

            <p>{career.projectContent}</p>

            <div className="tag-list">
              {career.skillNames.map((skill) => (
                <span key={skill} className="tag">
                  {skill}
                </span>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}