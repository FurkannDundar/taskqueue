import { useState } from "react";
import TaskPicker from "./TastPicker";
import axiosInstance from "../api/apiClient";
import PriorityPicker from "./PriorityPicker";
import RetryPicker from "./RetryPicker";
import CountPicker from "./CountPicker";

function TaskForm() {
  const [task, setTask] = useState("");
  const [priority, setPriority] = useState("");
  const [maxRetry, setMaxRetry] = useState("");
  const [count, setCount] = useState("");
  const [error, setError] = useState(false);

  function handleReset() {
    setTask("");
    setPriority("");
    setMaxRetry("");
    setCount("");
  }

  function handleSubmit(event) {
    event.preventDefault();

    if (!priority || !task || !maxRetry || !count) {
      setError("Bütün Alanların Doldurulması Zorunludur");
      return;
    }

    const taskObject = {
      priority,
      taskType: task,
      maxRetry,
      count,
    };
    axiosInstance.post("/task/create", taskObject);
  }

  return (
    <>
      <div className="min-h-screen flex items-center justify-center bg-gray-100">
        <div className="bg-white rounded-lg shadow-lg border border-gray-200 p-8 w-full max-w-4xl">
          <h2 className="text-2xl font-bold mb-6 text-gray-800">TaskQueue</h2>

          <div className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <TaskPicker task={task} setTask={setTask} />
              <PriorityPicker priority={priority} setPriority={setPriority} />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <CountPicker count={count} setCount={setCount} />
              <RetryPicker maxRetry={maxRetry} setMaxRetry={setMaxRetry} />
            </div>

            <div className="grid grid-cols-2 gap-4 mt-8">
              <button
                className="bg-gray-500 hover:bg-gray-600 text-white font-semibold py-3 px-6 rounded-lg shadow-md transition-colors"
                onClick={handleReset}
              >
                Sıfırla
              </button>

              <button
                className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 px-6 rounded-lg shadow-md transition-colors"
                onClick={handleSubmit}
              >
                Gönder
              </button>
            </div>
          </div>
          {error && (
            <div className="mt-6 bg-red-50 border-l-4 border-red-500 rounded-lg p-4 shadow-sm">
              <div className="flex items-start">
                <div className="shrink-0">
                  <span className="text-2xl">❌</span>
                </div>
                <div className="ml-3 flex-1">
                  <h3 className="text-sm font-semibold text-red-800">
                    {error}
                  </h3>
                  <p className="mt-1 text-sm text-red-700"></p>
                </div>
                <button
                  onClick={() => setError(false)}
                  className="shrink-0 ml-4 text-red-500 hover:text-red-700 transition-colors"
                >
                  <span className="text-xl">✕</span>
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </>
  );
}

export default TaskForm;
