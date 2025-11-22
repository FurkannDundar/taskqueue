function TaskPicker({ task, setTask }) {
  return (
    <>
      <div className="flex justify-center p-4">
        <label className="underline mr-3">Pick Your Task</label>
        <select
          value={task}
          className="border-2 ml-4 p-2"
          onChange={(event) => {
            setTask(event.target.value);
          }}
        >
          <option value=""></option>
          <option value="Send Email">Send Email</option>
          <option value="Prepare Report">Prepare Report</option>
          <option value="Ping Google.com">Ping Google.com</option>
        </select>
      </div>
    </>
  );
}

export default TaskPicker;
