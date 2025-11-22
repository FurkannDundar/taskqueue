import { useState } from "react";
import TaskPicker from "./TastPicker";
import axiosInstance from "../api/apiClient";
function TaskForm() {
  const [task, setTask] = useState("");
  const [priority, setPriority] = useState("");
  const [maxRetry, setMaxRetry] = useState("");

  function handleSubmit(event) {
    event.preventDefault();
    const obj = { priority, task, maxRetry };
    axiosInstance.post("/task", obj);
  }

  return (
    <>
      <div className="w-[80%] m-auto flex justify-center min-h-screen items-center">
        <form className="border-4 p-6 w-[70%]" onSubmit={handleSubmit}>
          <TaskPicker task={task} setTask={setTask} />
          <div className="flex justify-center align-middle">
            <div className=" flex items-center">Pick the priority</div>
            <div className="p-3 ml-4">
              <label>
                <input
                  type="checkbox"
                  name="priority"
                  value="Low"
                  className="mr-3"
                  checked={priority === "Low"}
                  onChange={() => {
                    setPriority("Low");
                  }}
                />
                Low
              </label>
              <label>
                <input
                  type="checkbox"
                  name="priority"
                  value="Medium"
                  className="mr-3"
                  checked={priority === "Medium"}
                  onChange={() => {
                    setPriority("Medium");
                  }}
                />
                Medium
              </label>
              <label>
                <input
                  type="checkbox"
                  name="priority"
                  value="High"
                  className="mr-3"
                  checked={priority === "High"}
                  onChange={() => {
                    setPriority("High");
                  }}
                />
                High
              </label>
              <label>
                <input
                  type="checkbox"
                  name="priority"
                  value="Critical"
                  checked={priority === "Critical"}
                  onChange={() => {
                    setPriority("Critical");
                  }}
                />
                Critical
              </label>
            </div>
          </div>
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
