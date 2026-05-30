export type SkillGraphNode = {
  id: number;
  label: string;
  totalMonths: number;
};

export type SkillGraphEdge = {
  source: number;
  target: number;
  weight: number;
  coMonths: number;
};

export type SkillGraphResponse = {
  nodes: SkillGraphNode[];
  edges: SkillGraphEdge[];
};

export type CareerResponse = {
  careerId: number;
  projectId: number;
  projectName: string;
  projectContent: string;
  divisionName: string;
  companyName: string;
  startTime: string;
  endTime: string | null;
  skillNames: string[];
};

export type SkillExperienceResponse = {
  skillId: number;
  skillName: string;
  totalYears: number;
  totalMonths: number;
};

export type ProfileResponse = {
  hrid: string;
  name: string;
  mailAddress: string;
  free: string;
  rankName: string;
  careers: CareerResponse[];
  skillExperiences: SkillExperienceResponse[];
};

export type PersonalSkillNode = {
  id: number;
  label: string;
  months: number;
  normalizedSize: number;
};

export type PersonalSkillEdge = {
  source: number;
  target: number;
  months: number;
  normalizedWeight: number;
};

export type PersonalSkillGraphResponse = {
  nodes: PersonalSkillNode[];
  edges: PersonalSkillEdge[];
};

export type SimilarProfileResponse = {
  hrid: string;
  name: string;
  similarity: number;
  commonSkillNames: string;
};

export type ExpertResponse = {
  hrid: string;
  name: string;
  mailAddress: string;
  rankName: string;
  totalMonths: number;
};
