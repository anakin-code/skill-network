import type { CareerResponse } from "../types/api";

type Props = {
  careers: CareerResponse[];
};

export default function CareerPanel({ careers }: Props) {
  return (
    <div className="card">
      <h3>経歴</h3>
      {careers.length === 0 && <p className="muted">経歴がありません</p>}
      {careers.map((career) => (
        <div key={career.careerId} className="career-item">
          <strong>{career.projectName}</strong>
          <p>{career.companyName} / {career.divisionName}</p>
          <small>{career.startTime} - {career.endTime ?? "現在"}</small>
          <p>{career.projectContent}</p>
          <div>
            {career.skillNames.map((skill) => (
              <span key={skill} className="badge">{skill}</span>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
}
