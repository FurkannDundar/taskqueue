import { useState } from "react";
import TaskPicker from "./TastPicker";
import axiosInstance from "../api/apiClient";
import PriorityPicker from "./PriorityPicker";
function TaskForm() {
  const [task, setTask] = useState("");
  const [priority, setPriority] = useState("");
  const [maxRetry, setMaxRetry] = useState("");

  function handleSubmit(event) {
    event.preventDefault();
    const obj = {
      taskPriority: priority,
      taskType: task,
      maxRetry,
    };
    console.log(obj);
    axiosInstance.post("/task/create", obj);
  }

  return (
    <>
      <div className="w-[80%] m-auto flex justify-center min-h-screen items-center">
        <form className="border-4 p-6 w-[70%]" onSubmit={handleSubmit}>
          <TaskPicker task={task} setTask={setTask} />
          <PriorityPicker priority={priority} setPriority={setPriority} />
          <div className="flex justify-center">
            <label>Max Retry</label>
            <select
              value={maxRetry}
              className="border-2 ml-4"
              onChange={(event) => {
                setMaxRetry(event.target.value);
              }}
            >
              <option value="0">0</option>
              <option value="3">3</option>
              <option value="5">5</option>
              <option value="10">10</option>
            </select>
          </div>
          <div className="flex justify-center">
            <button type="submit" className="border-2 p-2">
              Submit
            </button>
          </div>
        </form>
      </div>
    </>
  );
}

export default TaskForm;
