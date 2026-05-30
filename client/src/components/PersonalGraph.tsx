import { useMemo } from "react";
import ReactFlow, {
  Background,
  Controls,
  MarkerType,
  type Edge,
  type Node,
} from "reactflow";
import "reactflow/dist/style.css";
import type { PersonalSkillGraphResponse } from "../types/api";

type Props = {
  graph?: PersonalSkillGraphResponse;
};

export default function PersonalGraph({ graph }: Props) {
  const nodes: Node[] = useMemo(() => {
    if (!graph) return [];

    return graph.nodes.map((node, index) => {
      const angle =
        (2 * Math.PI * index) / Math.max(graph.nodes.length, 1);

      const radius = 170;

      return {
        id: String(node.id),
        position: {
          x: 230 + Math.cos(angle) * radius,
          y: 210 + Math.sin(angle) * radius,
        },
        data: {
          label: `${node.label}\n${node.months}ヶ月`,
        },
        style: {
          width: 80 + node.normalizedSize * 45,
          height: 80 + node.normalizedSize * 45,
          borderRadius: "50%",
          background: "#e9fff2",
          border: "2px solid #42b883",
          fontWeight: 700,
          whiteSpace: "pre-line",
          textAlign: "center",
          fontSize: 12,
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        },
      };
    });
  }, [graph]);

  const edges: Edge[] = useMemo(() => {
    if (!graph) return [];

    return graph.edges
      .filter((edge) => edge.normalizedWeight >= 0.35)
      .map((edge) => ({
        id: `${edge.source}-${edge.target}`,
        source: String(edge.source),
        target: String(edge.target),
        type: "straight",
        markerEnd: undefined,
        style: {
          strokeWidth: 1 + edge.normalizedWeight * 4,
          stroke: "#9ca3af",
        },
      }));
  }, [graph]);

  return (
    <div className="card">
      <h3>個人スキルネットワーク</h3>

      <p className="muted">
        ノードサイズ：経験月数、線の太さ：共起の強さ
      </p>

      <div style={{ width: "100%", height: "480px" }}>
        <ReactFlow
          nodes={nodes}
          edges={edges}
          fitView
          fitViewOptions={{
            padding: 0.25,
          }}
          minZoom={0.4}
          maxZoom={1.8}
        >
          <Background />
          <Controls />
        </ReactFlow>
      </div>
    </div>
  );
}