export type SkillGraphNode = {
  id: number;
  label: string;
  type: string;
};

export type SkillGraphEdge = {
  source: number;
  target: number;
  weight: number;
};

export type SkillGraphResponse = {
  nodes: SkillGraphNode[];
  edges: SkillGraphEdge[];
};

export type PersonalSkillGraphNode = {
  id: number;
  label: string;
  months: number;
  normalizedSize: number;
};

export type PersonalSkillGraphEdge = {
  source: number;
  target: number;
  months: number;
  normalizedWeight: number;
};

export type PersonalSkillGraphResponse = {
  hrid: string;
  nodes: PersonalSkillGraphNode[];
  edges: PersonalSkillGraphEdge[];
};

export type CareerResponse = {
  careerId: number;
  projectId: number;
  projectContent: string;
  projectName: string;
  startTime: string;
  endTime: string | null;
  skillNames: string[];
};

export type ProfileResponse = {
  hrid: string;
  name: string;
  mailAddress: string;
  rankName: string;
  free: string;
  careers: CareerResponse[];
};

export type ExpertResponse = {
  hrid: string;
  name: string;
  mailAddress: string;
  rankName: string;
  skillNames: string;
  years: number;
  months: number;
  score: number;
};

export type SimilarProfileResponse = {
  hrid: string;
  name: string;
  mailAddress: string;
  rankName: string;
  skillNames: string;
  commonSkillNames: string;
  commonSkillCount: number;
  similarity: number;
};