import React, { useEffect, useMemo, useState } from "react";

import {
    Box,
    Paper,
    Table,
    Text,
    Badge,
    ActionIcon,
    Button,
    TextInput,
    Group,
    Stack,
    Modal,
    ScrollArea,
    Select,
    Textarea,
    LoadingOverlay,
    SegmentedControl,
} from "@mantine/core";

import { useDisclosure } from "@mantine/hooks";

import {
    IconEdit,
    IconTrash,
    IconSearch,
    IconChevronUp,
    IconChevronDown,
    IconSelector,
} from "@tabler/icons-react";

import "@mantine/core/styles.css";
import { getDoctorDropdown } from "../../../Service/DoctorProfileService";
import { DateTimePicker } from "@mantine/dates";
import { useForm } from "@mantine/form";
import { appointmentReasons } from "../../../Data/DropDownData";
import { useSelector } from "react-redux";
import { cancelAppointment, getAppointmentWithDetailsByPatientId, scedultAppointment } from "../../../Service/AppointmentService";
import { errorNotification, successNotification } from "../../../Utility/NotificationUtil";
import { modals } from "@mantine/modals";


// ===============================
// Appointment Interface
// ===============================

interface AppointmentData {
    id: number;
    patientName: string | null;
    doctorName: string;
    appointmentTime: string;
    reason: string;
    notes: string;
    status: "SCHEDULED" | "COMPLETED" | "CANCELLED";
}

type SortKey = keyof AppointmentData;

type SortDirection = "asc" | "desc";



// ===============================
// Component
// ===============================

const Appointment = () => {

    const [doctors, setDoctors] = useState<any[]>([]);
    const [appointments, setAppointments] = useState<AppointmentData[]>([]);
    const user = useSelector((state: any) => state.user);
    const [tab, setTab] = useState<string>('Today');

    const [loading, setLoading] = useState<boolean>(false);



    const loadAppointments = async () => {
        try {
            const data = await getAppointmentWithDetailsByPatientId(user.profileId);

            setAppointments(
                data.map((appointment: any): AppointmentData => ({
                    id: appointment.id,
                    patientName: appointment.patientName,
                    doctorName: appointment.doctorName,
                    appointmentTime: appointment.appointmentTime,
                    reason: appointment.reason || "-",
                    notes: appointment.notes || "",
                    status: appointment.status,
                }))
            );
        } catch (error) {
            console.error("Error while retrieving appointments:", error);
        }
    };

    useEffect(() => {

        loadAppointments();

        getDoctorDropdown().then((data) => {
            console.log(data);
            setDoctors(data.map((doctor: any) => ({
                value: "" + doctor.id,
                label: doctor.name
            })));
        }).catch((error) => {
            console.error("error for fetcing doctor", error);
        });

    }, []);

    const form = useForm({

        initialValues: {
            doctorId: '',
            patientId: user.profileId,
            appointmentTime: new Date(),
            reason: '',
            notes: ''

        },
        validate:
        {
            doctorId: (value) => !value ? 'Doctore is required' : null,
            appointmentTime: (value) => !value ? 'appointment time is required' : null,
            reason: (value) => !value ? 'Reason is required' : null,

        },
    })


    const handleSubmit = (value: any) => {

        setLoading(true);
        console.log("summited data is ", value);

        const appointmentTime = value.appointmentTime
            ? new Date(value.appointmentTime).toISOString().slice(0, 19)
            : null;

        const requestData = {
            ...value,
            appointmentTime: appointmentTime,
        };


        scedultAppointment(requestData).then(() => {
            // setLoading(false);
            close();
            form.reset();

            loadAppointments();

            successNotification("Appointment Sceduled Succesfully!!");

        }).catch((err) => {
            // setLoading(false);
            errorNotification(err.response?.data?.errorMessage || "Faild to schedule appointment");
            console.log("Error while Sceduling : ", err);
        })
            .finally(() => {
                setLoading(false);
            })

    }




    // Modal
    const [opened, { open, close }] = useDisclosure(false);

    // Search
    const [search, setSearch] = useState("");

    // Sorting
    const [sortBy, setSortBy] = useState<SortKey>("id");

    const [sortDirection, setSortDirection] =
        useState<SortDirection>("asc");


    // ===============================
    // Sorting Handler
    // ===============================

    const handleSort = (column: SortKey) => {

        if (sortBy === column) {

            setSortDirection((previous) =>
                previous === "asc" ? "desc" : "asc"
            );

        } else {

            setSortBy(column);

            setSortDirection("asc");
        }
    };


    // ===============================
    // Search + Sorting
    // ===============================

    const sortedAppointments = useMemo(() => {

        const searchText = search
            .toLowerCase()
            .trim();


        // -------------------------------
        // Search
        // -------------------------------

        const filtered = appointments.filter(
            (appointment) => {

                return (

                    (appointment.patientName || "")
                        .toLowerCase()
                        .includes(searchText)

                    ||

                    appointment.doctorName
                        .toLowerCase()
                        .includes(searchText)

                    ||

                    appointment.reason
                        .toLowerCase()
                        .includes(searchText)

                    ||

                    appointment.status
                        .toLowerCase()
                        .includes(searchText)

                    ||

                    appointment.appointmentTime
                        .toLowerCase()
                        .includes(searchText)

                    ||

                    appointment.id
                        .toString()
                        .includes(searchText)
                );
            }
        );


        // -------------------------------
        // Sorting
        // -------------------------------

        return [...filtered].sort((a, b) => {

            const valueA = a[sortBy];

            const valueB = b[sortBy];

            let comparison = 0;


            if (
                typeof valueA === "number" &&
                typeof valueB === "number"
            ) {

                comparison = valueA - valueB;

            } else {

                comparison = String(valueA).localeCompare(
                    String(valueB),
                    undefined,
                    {
                        numeric: true,
                        sensitivity: "base",
                    }
                );
            }


            return sortDirection === "asc"
                ? comparison
                : -comparison;
        });

    }, [appointments, search, sortBy, sortDirection]);


    // ===============================
    // Edit
    // ===============================

    const handleEdit = (id: number) => {

        console.log(
            "Edit appointment:",
            id
        );
    };


    // ===============================
    // Delete
    // ===============================

    const handleDelete = (id: number) => {

        modals.openConfirmModal({
            title: <span className="text-xl font-semibold">Are You Sure??</span>,
            centered: true,
            children: (
                <Text size="sm">
                    Cancle this Appointment ?
                </Text>
            ),
            labels: { confirm: 'Confirm', cancel: 'Cancel' },

            onConfirm: () => {

                cancelAppointment(id).then(() => {
                    successNotification("Appointment Cancled succesfully")
                    loadAppointments();
                }).catch((error) => {
                    errorNotification("some erroe occured");
                })

            }
        });

        console.log(
            "Delete appointment:",
            id
        );
    };


    // ===============================
    // Sort Icon
    // ===============================

    const SortIcon = ({
        column,
    }: {
        column: SortKey;
    }) => {

        if (sortBy !== column) {

            return (
                <IconSelector
                    size={16}
                    stroke={1.5}
                />
            );
        }


        if (sortDirection === "asc") {

            return (
                <IconChevronUp
                    size={16}
                    stroke={2}
                />
            );
        }


        return (
            <IconChevronDown
                size={16}
                stroke={2}
            />
        );
    };


    // ===============================
    // Sortable Header
    // ===============================

    const SortableHeader = ({
        column,
        children,
    }: {
        column: SortKey;
        children: React.ReactNode;
    }) => {

        return (
            <Table.Th>

                <Button
                    variant="subtle"
                    color="gray"
                    size="compact-sm"
                    onClick={() =>
                        handleSort(column)
                    }
                    rightSection={
                        <SortIcon
                            column={column}
                        />
                    }
                    styles={{
                        root: {
                            padding: 0,
                            fontWeight: 600,
                        },
                    }}
                >
                    {children}
                </Button>

            </Table.Th>
        );
    };


    // ===============================
    // Status Badge
    // ===============================

    const getStatusColor = (
        status: AppointmentData["status"]
    ) => {

        switch (status) {

            case "SCHEDULED":
                return "yellow";

            case "COMPLETED":
                return "green";

            case "CANCELLED":
                return "red";

            default:
                return "gray";
        }
    };


    const filteredAppointments = sortedAppointments.filter((appointment) => {
        const appointmentDate = new Date(appointment.appointmentTime);
        const today = new Date();

        const appointmentDay = new Date(
            appointmentDate.getFullYear(),
            appointmentDate.getMonth(),
            appointmentDate.getDate()
        ).getTime();

        const todayDay = new Date(
            today.getFullYear(),
            today.getMonth(),
            today.getDate()
        ).getTime();

        if (tab === "Today") {
            return appointmentDay === todayDay;
        }

        if (tab === "Upcoming") {
            return appointmentDay > todayDay;
        }

        if (tab === "Past") {
            return appointmentDay < todayDay;
        }

        return true;
    });

    // ===============================
    // Table Rows
    // ===============================

    const rows = filteredAppointments.map(
        (appointment, index) => (

            <Table.Tr
                key={appointment.id}
            >

                {/* ID */}

                <Table.Td>
                    <Text
                        size="sm"
                        fw={500}
                    >
                        {index + 1}
                    </Text>
                </Table.Td>


                {/* Doctor */}

                <Table.Td>

                    <Text size="sm">
                        {appointment.doctorName}
                    </Text>

                </Table.Td>




                {/* Date */}

                <Table.Td>

                    <Text size="sm">
                        {new Date(appointment.appointmentTime).toLocaleDateString()}
                    </Text>

                </Table.Td>


                {/* Time */}

                <Table.Td>

                    <Text size="sm">
                        {new Date(appointment.appointmentTime).toLocaleTimeString([], {
                            hour: "2-digit",
                            minute: "2-digit",
                        })}
                    </Text>

                </Table.Td>


                {/* Reason */}

                <Table.Td>

                    <Text size="sm">
                        {appointment.reason}
                    </Text>

                </Table.Td>
                {/* Notes */}

                <Table.Td>

                    <Text size="sm" maw={180} truncate>
                        {appointment.notes || "-"}
                    </Text>

                </Table.Td>


                {/* Status */}

                <Table.Td>

                    <Badge w="80"
                        color={getStatusColor(
                            appointment.status
                        )}
                        variant="light"
                        size="sm"
                    >
                        {appointment.status}
                    </Badge>

                </Table.Td>


                {/* Actions */}

                <Table.Td>

                    <Group
                        gap={5}
                        justify="center"
                    >

                        <ActionIcon
                            variant="light"
                            color="blue"
                            size="sm"
                            onClick={() =>
                                handleEdit(
                                    appointment.id
                                )
                            }
                        >

                            <IconEdit
                                size={16}
                            />

                        </ActionIcon>


                        <ActionIcon
                            variant="light"
                            color="red"
                            size="sm"
                            onClick={() =>
                                handleDelete(
                                    appointment.id
                                )
                            }

                        >

                            <IconTrash
                                size={16}
                            />

                        </ActionIcon>

                    </Group>

                </Table.Td>

            </Table.Tr>
        )
    );


    // ===============================
    // UI
    // ===============================

    return (

        <Box
            p="lg"
        >


            {/* =========================
                PAGE HEADER
            ========================== */}

            <Group
                justify="space-between"
                mb="lg"
            >

                <Stack gap={2}>
                    <div className="flex flex-col">

                        <Text
                            size="xl"
                            fw={600}
                            c="#1f2937"
                        >
                            Appointments
                        </Text>

                        <Text
                            size="sm"
                            c="dimmed"
                        >
                            Manage patient appointments
                        </Text>

                    </div>


                </Stack>
                <div className="">
                    <SegmentedControl
                        className="gap-5"                        
                        variant="filled"
                        
                        color="blue"
                        value={tab}
                        onChange={setTab}
                        data={["Past", "Today", "Upcoming"]}
                    />
                </div>


                <Button
                    color="cyan"
                    onClick={open}
                    radius="md"
                >
                    + Add Appointment
                </Button>

            </Group>


            {/* =========================
                TABLE CARD
            ========================== */}

            <Paper
                withBorder
                radius="md"
                shadow="xs"
                style={{
                    overflow: "hidden",
                }}
            >

                {/* =========================
                    SEARCH BAR
                ========================== */}

                <Group
                    justify="space-between"
                    p="md"
                    style={{
                        borderBottom:
                            "1px solid #e5e7eb",
                    }}
                >

                    <TextInput
                        placeholder="Search appointments..."
                        value={search}
                        onChange={(event) =>
                            setSearch(
                                event.currentTarget.value
                            )
                        }
                        leftSection={
                            <IconSearch
                                size={16}
                            />
                        }
                        w={320}
                        radius="md"
                    />


                    <Text
                        size="sm"
                        c="dimmed"
                    >
                        {sortedAppointments.length}{" "}
                        appointments
                    </Text>

                </Group>


                {/* =========================
                    TABLE
                ========================== */}

                <ScrollArea>

                    <Table
                        striped
                        highlightOnHover
                        withColumnBorders={false}
                        verticalSpacing="sm"
                        horizontalSpacing="md"
                        miw={950}
                    >

                        {/* HEADER */}

                        <Table.Thead>

                            <Table.Tr
                                style={{
                                    backgroundColor:
                                        "#f8fafc",
                                }}
                            >

                                <SortableHeader column="id">
                                    No.
                                </SortableHeader>

                                <SortableHeader column="doctorName">
                                    Doctor
                                </SortableHeader>


                                <SortableHeader column="appointmentTime">
                                    Date
                                </SortableHeader>

                                <SortableHeader column="appointmentTime">
                                    Time
                                </SortableHeader>

                                <SortableHeader column="reason">
                                    Reason
                                </SortableHeader>

                                <SortableHeader column="notes">
                                    Notes
                                </SortableHeader>

                                <SortableHeader column="status">
                                    Status
                                </SortableHeader>


                                <Table.Th
                                    style={{
                                        textAlign:
                                            "center",
                                    }}
                                >
                                    Actions
                                </Table.Th>

                            </Table.Tr>

                        </Table.Thead>


                        {/* BODY */}

                        <Table.Tbody>

                            {filteredAppointments.length >
                                0 ? (

                                rows

                            ) : (

                                <Table.Tr>

                                    <Table.Td
                                        colSpan={8}
                                    >

                                        <Text
                                            ta="center"
                                            c="dimmed"
                                            py="xl"
                                        >
                                            No appointments
                                            found
                                        </Text>

                                    </Table.Td>

                                </Table.Tr>

                            )}

                        </Table.Tbody>

                    </Table>

                </ScrollArea>

            </Paper>


            {/* =========================
                ADD APPOINTMENT MODAL
            ========================== */}

            <Modal
                opened={opened}
                onClose={close}
                title={<div className="font-medium text-center text-xl text-primary-700"> Scedule Appointment</div>}
                centered
                radius="md"
                size={"md"}
            >
                <LoadingOverlay visible={loading} zIndex={1000} overlayProps={{ radius: "sm", blur: 2 }} />

                <form onSubmit={form.onSubmit(handleSubmit)} className="grid grid-cols-1 gap-5">

                    <Select {...form.getInputProps("doctorId")} withAsterisk data={doctors} label="Doctor" placeholder="Select Doctor" />


                    <DateTimePicker minDate={new Date()} {...form.getInputProps("appointmentTime")} withAsterisk label="Appointment time" placeholder="Select appointment time" />

                    <Select withAsterisk {...form.getInputProps("reason")} data={appointmentReasons} label="Reason of Appointment" placeholder="Enter Reason for Appointment" />

                    <Textarea {...form.getInputProps("notes")} label="Additional Notes" placeholder="Enter any Additional notes" />

                    <Button type="submit" variant="filled" >Submit</Button>
                </form>

            </Modal>

        </Box>
    );
};


export default Appointment;